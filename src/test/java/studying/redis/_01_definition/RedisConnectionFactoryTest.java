package studying.redis._01_definition;

import io.lettuce.core.api.StatefulConnection;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;
import studying.redis._01_definition.configurations.RedisProperties;

@SpringBootTest
public class RedisConnectionFactoryTest {

  @Autowired
  private RedisProperties redisProperties;

  // 실제로 사용하는 native connection 공유
  @Test
  void native_connection_공유_테스트() throws Exception {
    RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfiguration);
    connectionFactory.setShareNativeConnection(true);
    connectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);
    redisTemplate.afterPropertiesSet();

    Set<String> nativeConnections = new HashSet<>();
    int threadCount = 10;
    CountDownLatch latch = new CountDownLatch(threadCount);
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        try {
          redisTemplate.execute((RedisCallback<Void>) connection -> {
            // NativeConnection ID 추출 (같아야 함)
            String redisConnId = System.identityHashCode(connection) + "";
            if (connection instanceof LettuceConnection) {
              LettuceConnection lettuceConn = (LettuceConnection) connection;
              Object nativeConn = lettuceConn.getNativeConnection();
              String nativeConnId = System.identityHashCode(nativeConn) + "";
              nativeConnections.add(nativeConnId);
//              System.out.printf("RedisConn: %s, NativeConn: %s%n", redisConnId, nativeConnId);
            }
            return null;
          }, true);
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await(10, TimeUnit.SECONDS);
    System.out.println("Native Connection size = " + nativeConnections.size());
    System.out.println("Native Connections = " + nativeConnections);
  }

  // 실제로 사용하는 native connection 은 모두 다름
  @Test
  void native_connection_공유하지_않는_테스트() throws Exception {
    RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfiguration);
    connectionFactory.setShareNativeConnection(false);
    connectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);
    redisTemplate.afterPropertiesSet();

    Set<String> nativeConnections = new HashSet<>();
    int threadCount = 10;
    CountDownLatch latch = new CountDownLatch(threadCount);
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        try {
          redisTemplate.execute((RedisCallback<Void>) connection -> {
            // NativeConnection ID 추출 (같아야 함)
            String redisConnId = System.identityHashCode(connection) + "";
            if (connection instanceof LettuceConnection) {
              LettuceConnection lettuceConn = (LettuceConnection) connection;
              Object nativeConn = lettuceConn.getNativeConnection();
              String nativeConnId = System.identityHashCode(nativeConn) + "";
              nativeConnections.add(nativeConnId);
//              System.out.printf("RedisConn: %s, NativeConn: %s%n", redisConnId, nativeConnId);
            }
            return null;
          }, true);
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await(10, TimeUnit.SECONDS);
    System.out.println("Native Connection size = " + nativeConnections.size());
    System.out.println("Native Connections = " + nativeConnections);
  }

  // 실제로 사용하는 native connection 공유, 커넥션 풀 사용
  @Test
  void native_connection_공유하지_않고_커넥션_풀_사용_테스트() throws Exception {
    // Connection pool 설정
    GenericObjectPoolConfig<StatefulConnection<?, ?>> poolConfig = new GenericObjectPoolConfig<>();
    poolConfig.setMaxTotal(2);
    poolConfig.setMaxWait(Duration.ofSeconds(3));
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setTestWhileIdle(true);

    LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
        .poolConfig(poolConfig)
        .commandTimeout(Duration.ofSeconds(5))
        .build();
    RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfiguration, clientConfig);
    connectionFactory.setShareNativeConnection(false);
    connectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);
    redisTemplate.afterPropertiesSet();

    Set<String> nativeConnections = new HashSet<>();
    int threadCount = 10;
    CountDownLatch latch = new CountDownLatch(threadCount);
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        try {
          redisTemplate.execute((RedisCallback<Void>) connection -> {
            // NativeConnection ID 추출 (같아야 함)
            String redisConnId = System.identityHashCode(connection) + "";
            if (connection instanceof LettuceConnection) {
              LettuceConnection lettuceConn = (LettuceConnection) connection;
              Object nativeConn = lettuceConn.getNativeConnection();
              String nativeConnId = System.identityHashCode(nativeConn) + "";
              nativeConnections.add(nativeConnId);
//              System.out.printf("RedisConn: %s, NativeConn: %s%n", redisConnId, nativeConnId);
            }
            return null;
          }, true);
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await(10, TimeUnit.SECONDS);
    System.out.println("Native Connection size = " + nativeConnections.size());
    System.out.println("Native Connections = " + nativeConnections);
  }

  @Test
  void 싱글쓰레드에서_lettuce_jedis_성능비교_순서() {
    RedisStandaloneConfiguration lettuceConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(lettuceConfiguration);
    connectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> lettuceTemplate = new RedisTemplate<>();
    lettuceTemplate.setConnectionFactory(connectionFactory);
    lettuceTemplate.afterPropertiesSet();

    long start = System.currentTimeMillis();
    int count = 1000;
    for (int i = 0; i < count; i++) {
      lettuceTemplate.opsForValue().set("key" + i, "value" + i);
      lettuceTemplate.opsForValue().get("key" + i);
    }
    long end = System.currentTimeMillis();
    System.out.println("Lettuce: " + (end - start) + "ms");
    RedisStandaloneConfiguration jedisConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisConfiguration);
    jedisConnectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> jedisTemplate = new RedisTemplate<>();
    jedisTemplate.setConnectionFactory(jedisConnectionFactory);
    jedisTemplate.afterPropertiesSet();

    start = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      jedisTemplate.opsForValue().set("key" + i, "value" + i);
      jedisTemplate.opsForValue().get("key" + i);
    }
    end = System.currentTimeMillis();
    System.out.println("Jedis: " + (end - start) + "ms");
  }

  @Test
  void 싱글쓰레드에서_jedis_lettuce_성능비교_순서() {
    RedisStandaloneConfiguration jedisConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisConfiguration);
    jedisConnectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> jedisTemplate = new RedisTemplate<>();
    jedisTemplate.setConnectionFactory(jedisConnectionFactory);
    jedisTemplate.afterPropertiesSet();

    long start = System.currentTimeMillis();
    int count = 1000;
    for (int i = 0; i < count; i++) {
      jedisTemplate.opsForValue().set("key" + i, "value" + i);
      jedisTemplate.opsForValue().get("key" + i);
    }
    long end = System.currentTimeMillis();
    System.out.println("Jedis: " + (end - start) + "ms");

    RedisStandaloneConfiguration lettuceConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(lettuceConfiguration);
    connectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> lettuceTemplate = new RedisTemplate<>();
    lettuceTemplate.setConnectionFactory(connectionFactory);
    lettuceTemplate.afterPropertiesSet();

    start = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      lettuceTemplate.opsForValue().set("key" + i, "value" + i);
      lettuceTemplate.opsForValue().get("key" + i);
    }
    end = System.currentTimeMillis();
    System.out.println("Lettuce: " + (end - start) + "ms");
  }

  @Test
  void 멀티쓰레드에서_jedis_lettuce_성능비교_순서() throws InterruptedException {
    RedisStandaloneConfiguration jedisConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    int threadCount = 200;
    poolConfig.setMaxTotal(threadCount);      // 스레드 수만큼 설정
    JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
        .usePooling()                      // 풀링 사용
        .poolConfig(poolConfig)            // 풀 설정 적용
        .build();

    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisConfiguration, clientConfig);
    jedisConnectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> jedisTemplate = new RedisTemplate<>();
    jedisTemplate.setConnectionFactory(jedisConnectionFactory);
    jedisTemplate.afterPropertiesSet();

    int count = 1000;
    ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

    CountDownLatch countDownLatch = new CountDownLatch(count);
    long start = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      int finalI = i;
      executorService.submit(() -> {
        try {
          jedisTemplate.opsForValue().set("key" + finalI, "value" + finalI);
          jedisTemplate.opsForValue().get("key" + finalI);
        } finally {
          countDownLatch.countDown();
        }
      });
    }
    countDownLatch.await();
    long end = System.currentTimeMillis();
    System.out.println("Jedis: " + (end - start) + "ms");

    RedisStandaloneConfiguration lettuceConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(lettuceConfiguration);
    connectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> lettuceTemplate = new RedisTemplate<>();
    lettuceTemplate.setConnectionFactory(connectionFactory);
    lettuceTemplate.afterPropertiesSet();

    CountDownLatch countDownLatch2 = new CountDownLatch(count);
    start = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      int finalI = i;
      executorService.submit(() -> {
        try {
          lettuceTemplate.opsForValue().set("key" + finalI, "value" + finalI);
          lettuceTemplate.opsForValue().get("key" + finalI);
        } finally {
          countDownLatch2.countDown();
        }
      });
    }
    countDownLatch2.await();
    end = System.currentTimeMillis();
    System.out.println("Lettuce: " + (end - start) + "ms");
  }

  @Test
  void 멀티쓰레드에서_pool_을_썼을때_jedis_lettuce_성능비교_순서() throws InterruptedException {
    RedisStandaloneConfiguration jedisConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    int threadCount = 200;
    poolConfig.setMaxTotal(threadCount);      // 스레드 수만큼 설정
    JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
        .usePooling()                      // 풀링 사용
        .poolConfig(poolConfig)            // 풀 설정 적용
        .build();

    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisConfiguration, clientConfig);
    jedisConnectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> jedisTemplate = new RedisTemplate<>();
    jedisTemplate.setConnectionFactory(jedisConnectionFactory);
    jedisTemplate.afterPropertiesSet();

    int count = 1000;
    ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

    CountDownLatch countDownLatch = new CountDownLatch(count);
    long start = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      int finalI = i;
      executorService.submit(() -> {
        try {
          jedisTemplate.opsForValue().set("key" + finalI, "value" + finalI);
          jedisTemplate.opsForValue().get("key" + finalI);
        } finally {
          countDownLatch.countDown();
        }
      });
    }
    countDownLatch.await();
    long end = System.currentTimeMillis();
    System.out.println("Jedis: " + (end - start) + "ms");

    RedisStandaloneConfiguration lettuceConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    GenericObjectPoolConfig<StatefulConnection<?, ?>> poolConfig2 = new GenericObjectPoolConfig<>();
    poolConfig.setMaxTotal(2);
    poolConfig.setMaxWait(Duration.ofSeconds(3));
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setTestWhileIdle(true);

    LettuceClientConfiguration clientConfig2= LettucePoolingClientConfiguration.builder()
        .poolConfig(poolConfig2)
        .build();
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(lettuceConfiguration, clientConfig2);
    connectionFactory.setShareNativeConnection(false);
    connectionFactory.afterPropertiesSet();

    RedisTemplate<Object, Object> lettuceTemplate = new RedisTemplate<>();
    lettuceTemplate.setConnectionFactory(connectionFactory);
    lettuceTemplate.afterPropertiesSet();

    CountDownLatch countDownLatch2 = new CountDownLatch(count);
    start = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      int finalI = i;
      executorService.submit(() -> {
        try {
          lettuceTemplate.opsForValue().set("key" + finalI, "value" + finalI);
          lettuceTemplate.opsForValue().get("key" + finalI);
        } finally {
          countDownLatch2.countDown();
        }
      });
    }
    countDownLatch2.await();
    end = System.currentTimeMillis();
    System.out.println("Lettuce: " + (end - start) + "ms");
  }
}
