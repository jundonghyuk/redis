# 레디스 정의

### 1. 레디스-스프링 통합

- Q1. 스프링에서 사용하는 레디스 관련 드라이버는 어떤 것이 있는가?
  - Q1-1. Lettuce 는 Netty 기반의 비동기 통신을 지원한다고 했는데, 동작 흐름이 어떻게 되는가?
    - Q1-1-1. CompletableFuture 형태로 결과를 반환한다고 하였는데, CompletableFuture 의 동작원리는 어떻게 되는가?
    - Q1-1-2. Netty 의 NioEventLoop 방식에서, 소켓의 읽기/쓰기 작업은 어떻게 처리되는가?
  - Q1-2. Lettuce 가 Netty 기반의 비동기 통신을 할 때 장점이 무엇이냐?
- Q2. Lettuce 의 Redis 로의 Connection 방식은 어떠한가?
- Q3. RedisTemplate 이 무엇이고, 동작 방식이 어떻게 되는가?

---

## Q1. 스프링에서 사용하는 레디스 관련 드라이버는 어떤 것이 있는가?

**A:**

Spring Data Redis 와 통합되어 있는 드라이버는 Lettuce 와 Jedis 드라이버가 있다.
추가로 Redisson 도 있지만, Spring Data Redis 와는 통합되어 있지 않다.
레디스 서버와의 연결을 책임지는 `RedisConnectionFactory` 의 구현체를 변경함으로써, 간단하게 적용할 수 있다.
![driver-features](/src/main/resources/_01_definition/driver_features.png)

가장 큰 차이는, 레디스로의 통신을 할 때 netty 기반의 비동기 통신을 지원하는지 여부이다.
Jedis 는 지원하지 않고, Lettuce 는 지원한다.
일반적으로 다양한 Features 을 지원하고, 비동기 통신을 지원하는 Lettuce 드라이버를 많이 사용할 것 같다.

### Q1-1. Lettuce 는 Netty 기반의 비동기 통신을 지원한다고 했는데, 동작 흐름이 어떻게 되는가? 

**A:**
 
MVC 아키텍쳐에서 Tomcat 쓰레드의 입장에서 먼저 보면, Tomcat 쓰레드가 레디스 명령 Task 를 제출하고 이벤트루프의 쓰레드를 깨우면서, 최종적으로 `CompletableFuture` 형태로 반환을 받고 block 된다.   
다른 쓰레드가 IO 작업을 끝내면 큐에서 `CompletableFuture` 를 꺼내서 `complete` 메서드를 호출하여 결과를 세팅하고 최종적으로 blocked 된 톰캣 쓰레드를 깨우게 된다. 

``` java
작업 제출 

AbstractChannelHandlerContext#write(Object msg, boolean flush, ChannelPromise promise) : void

final WriteTask task = WriteTask.newInstance(next, m, promise, flush);
if (!safeExecute(executor, task, promise, m, !flush)) {
    // We failed to submit the WriteTask. We need to cancel it so we decrement the pending bytes
    // and put it back in the Recycler for re-use later.
    //
    // See https://github.com/netty/netty/issues/8343.
    task.cancel();
}

-->
AbstractChannelHandlerContext#safeExecute(EventExecutor executor, Runnable runnable, ChannelPromise promise, Object msg, boolean lazy) : boolean

executor.execute(runnable);

-->
SingleThreadEventExecutor#execute(Runnable task, boolean immediate) : void

boolean inEventLoop = inEventLoop();
addTask(task);
if (!inEventLoop) {
    startThread();
    ...
}
```

``` java
대기중인 쓰레드 깨우기

CommandHandler#decode(ChannelHandlerContext ctx, ByteBuf buffer) : void

if (!decode(ctx, buffer, command)) { // 결과 세팅
  ...
}


if (canComplete(command)) {
  stack.poll(); // RedisCommand 큐에서 꺼내기
  
  try {
    complete(command);  // 대기중인 쓰레드 깨우기
  } catch (Exception e) {
    ...
  }
}

```
IO 통신을 담당하는 Netty EventLoop 입장에서 보면, 일단 `NioEventLoop` 는 싱글 쓰레드로 동작한다.
이 쓰레드는 `Queue`에 등록된 `Task`들을 처리하거나, IO 작업을 한다. 이 두 작업의 비율은 IO ratio 라는 값으로 조절할 수 있다.

#### Q1-1-1. CompletableFuture 형태로 결과를 반환한다고 하였는데, CompletableFuture 의 동작원리는 어떻게 되는가?

**A:**

CompletableFuture 의 `get` 메서드를 호출하면, 호출한 쓰레드는 내부 `Queue` 에 등록되고, LockSupport 의 `park` 메서드를 호출하여 block 된다.  
다른 쓰레드에서, 결과값과 함께 `complete` 메서드를 호출하면, 내부 `Queue` 에서 해당 쓰레드를 꺼내고, `LockSupport` 의 `unpark` 메서드를 호출하여 block 상태를 해제한다.
이제 결과가 있으니 while 문을 빠져나와서 결과를 반환한다.
``` java
LettuceConnection#doInvoke(RedisClusterAsyncCommands<byte[], byte[]> connection, boolean statusCommand) : LettuceInvoker

return new LettuceInvoker(connection, (future, converter, nullDefault) -> {
	try {
		Object result = await(future.get());
		return result != null ? converter.convert(result) : nullDefault.get();
	} catch (Exception ex) {
		throw convertLettuceAccessException(ex);
	}
});
```

``` java
CompletableFuture#waitingGet(boolean interruptible) : Object

while ((r = result) == null) {
        if (q == null) {
            q = new Signaller(interruptible, 0L, 0L);
            if (Thread.currentThread() instanceof ForkJoinWorkerThread)
                ForkJoinPool.helpAsyncBlocker(defaultExecutor(), q);
        }
        else if (!queued)
            queued = tryPushStack(q);
        else if (interruptible && q.interrupted) {
            q.thread = null;
            cleanStack();
            return null;
        }
        else {
            try {
                ForkJoinPool.managedBlock(q);
            } catch (InterruptedException ie) { // currently cannot happen
                q.interrupted = true;
            }
        }
    }
```

#### Q1-1-2. Netty 의 NioEventLoop 방식에서, 소켓의 읽기/쓰기 작업은 어떻게 처리되는가?

**A:**

### Q1-2. Lettuce 가 Netty 기반의 비동기 통신을 할 때 장점이 무엇이냐?

**A:**

Netty 의 `NioEventLoop` 를 사용하여 IO 작업을 비동기 통신으로 처리할 수 있다.  
MVC 모델에선 IO Task 를 건내준 호출 쓰레드가 block 되어서 성능(속도)면에선 이점이 크지 않다고 볼 수 있다.  
하지만, `Lettuce`의 `nativeConnection` 을 사용하면, 하나의 커넥션은 하나의 Channel 을 가지고있고, 이 채널은 소켓과 대응되는 개념이므로 OS 레벨에서는 불필요한 소켓을 많이 열 필요가 없어진다.  
이 커넥션은 여러 쓰레드가 공유(Thread safe 하기 때문)하므로 소켓이 차지하는 공간이나, 소켓을 생성 및 삭제하기 위한 불필요한 System call 을 줄일 수 있다.  
그렇기에 이 커넥션을 `sharedNativeConnection` 이라고도 한다.
물론 이 공유 쓰레드는 blocking 한 메서드에서는 사용되지 않고, 별도의 커넥션을 생성해서 사용한다. (pool config 를 설정하면 커넥션을 풀로 관리한다.)  
그 이유는, 레디스의 blocking 한 메서드(예를 들면 BLPOP 등)를 호출하였을 때, 커넥션을 공유하여 전송된 다른 커맨드들이 Blocked 될 가능성이 있기 때문이다.

**1. nativeConnection 공유한 경우** 
``` java
connectionFactory.setShareNativeConnection(true);
```

![native_connection](/src/main/resources/_01_definition/lettuce_native_connection.png)

레디스 서버와의 TCP 연결 1개만 생성되었다.

**2. nativeConnection 공유하지 않고 pool 사용을 하지 않은 경우**
``` java
connectionFactory.setShareNativeConnection(false);
```

![no_pool_connection](/src/main/resources/_01_definition/lettuce_no_pool_connection.png)

레디스 서버로의 TCP 연결이 많이 생성되고 즉시 종료되는 모습

**3. nativeConnection 공유하지 않고 pool 을 사용한 경우**
``` java
connectionFactory.setShareNativeConnection(false);
GenericObjectPoolConfig<StatefulConnection<?, ?>> poolConfig = new GenericObjectPoolConfig<>();
```

![pool_connection](/src/main/resources/_01_definition/lettuce_pool_connection.png)

레디스 서버로의 TCP 연결이 많이 생성되고 연결이 유지되는 모습

---

### Q2. Lettuce 의 Redis 로의 Connection 방식은 어떠한가?

**A:**

LettuceConnectionFactory 는 기본적으로 쓰레드 안전한 native connection 을 공유한다. (non-blocking, non-transactional operations)

- `connectionFactory.setShareNativeConnection(true);`

쓰레드마다 다른 native connection 을 사용할 수도 있다. 이 경우에 커넥션 풀링 설정을 해주는게 일반적이다.

- `connectionFactory.setShareNativeConnection(false);`

### Q3. RedisTemplate 이 무엇이고, 동작 방식이 어떻게 되는가?

**A:**

- 레디스 데이터 Access 를 할때 도와주는 Helper 클래스이다.
- Redis store 에 저장된 Binary data 를 자동으로 serialization/deserialization 해준다.

- 중심이 되는 메서드는 `execute(RedisCallback)`이며, RedisCallback 인터페이스를 구현하는 Redis 접근 코드를 지원한다.
  이 메서드는 `RedisConnection` 처리를 제공하므로, Redis 연결의 획득/종료를 명시적으로 신경 쓸 필요가 없고, Connection 생명주기 예외 처리도 할 필요가 없다.



---

## 📚 참고 문헌

- [Spring Data Redis docs [Drivers]](https://docs.spring.io/spring-data/redis/reference/redis/drivers.html)
- [Spring Data Redis docs [RedisTemplate]](https://docs.spring.io/spring-data/redis/reference/redis/template.html)