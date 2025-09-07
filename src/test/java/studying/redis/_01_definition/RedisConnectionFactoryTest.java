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
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
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
}
