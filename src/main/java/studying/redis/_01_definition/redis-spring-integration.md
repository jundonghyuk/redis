# 레디스 정의

### 1. 레디스-스프링 통합

- Q1. 스프링에서 사용하는 레디스 드라이버는 어떤 것이 있는가?
- Q2. Lettuce 의 Redis 로의 Connection 방식은 어떠한가?

---

### Q1. 스프링에서 사용하는 레디스 드라이버는 어떤 것이 있는가?

**A:**

Lettuce 와 Jedis 드라이버가 있다.

### Lettuce vs Jedis 비교

| **Feature**                     | **Lettuce**                                                                           | **Jedis**                                                                                                       |
|---------------------------------|---------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| **Standalone Connections**      | O                                                                                     | O                                                                                                               |
| **Master/Replica Connections**  | O                                                                                     | O                                                                                                               |
| **Redis Sentinel**              | Master Lookup, Sentinel Authentication, Replica Reads                                 | Master Lookup                                                                                                   |
| **Redis Cluster**               | Cluster Connections, Cluster Node Connections, Replica Reads                          | Cluster Connections, Cluster Node Connections                                                                   |
| **Transport Channels**          | TCP, OS-native TCP (epoll, kqueue), Unix Domain Sockets                               | TCP                                                                                                             |
| **Connection Pooling**          | O (using `commons-pool2`)                                                             | O (using `commons-pool2`)                                                                                       |
| **Other Connection Features**   | Singleton-connection sharing for non-blocking commands                                | Pipelining and Transactions mutually exclusive. Cannot use server/connection commands in pipeline/transactions. |
| **SSL Support**                 | O                                                                                     | O                                                                                                               |
| **Pub/Sub**                     | O                                                                                     | O                                                                                                               |
| **Pipelining**                  | O                                                                                     | O (Pipelining and Transactions mutually exclusive)                                                              |
| **Transactions**                | O                                                                                     | O (Pipelining and Transactions mutually exclusive)                                                              |
| **Datatype Support**            | Key, String, List, Set, Sorted Set, Hash, Server, Stream, Scripting, Geo, HyperLogLog | Key, String, List, Set, Sorted Set, Hash, Server, Stream, Scripting, Geo, HyperLogLog                           |
| **Reactive (non-blocking) API** | O                                                                                     | X                                                                                                               |

하지만, Spring Data Redis 를 사용하면 어떤 드라이버를 선택하든, Java Connector 를 이용하여 일관적으로 동작한다.

- `org.springframework.data.redis.connection`
    - `RedisConnection`: 레디스와의 통신을 직접적으로 담당하는 객체
        - `RedisConnection`클래스는 쓰레드로부터 안전 하지 않다. `StatefulRedisConnection`은 쓰레드로부터 안전할 수 있지만, Spring Data Redis의 `LettuceConnection`클래스 자체는 그렇지 않다.
        - `RedisConnection`클래스는 작업을 올바르게 완료하기 위해 보호되지 않는 변경 가능 상태를 유지하므로 여러 쓰레드에서 사용하는 것이 안전하지 않다. 이는 의도된 동작이다.
    - `RedisConnectionFactory`: `RedisConnection`을 관리하는 객체
        - `PersistenceExceptionTranslator의` 역할 또한 담당

### Q2. Lettuce 의 Redis 로의 Connection 방식은 어떠한가?

**A:**
LettuceConnectionFactory 는 기본적으로 쓰레드 안전한 native connection 을 공유한다. (non-blocking, non-transactional operations)

- `connectionFactory.setShareNativeConnection(true);`
  쓰레드마다 다른 native connection 을 사용할 수도 있다. 이 경우에 커넥션 풀링 설정을 해주는게 일반적이다.
- `connectionFactory.setShareNativeConnection(false);`

---

## 📚 참고 문헌

- [Spring Data Redis docs [Drivers]](https://docs.spring.io/spring-data/redis/reference/redis/drivers.html)