package studying.redis._01_definition.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@RequiredArgsConstructor
public class JedisConfiguration {

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    return new JedisConnectionFactory(redisConfiguration);
  }
}
