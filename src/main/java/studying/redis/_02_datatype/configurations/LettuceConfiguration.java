package studying.redis._02_datatype.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@RequiredArgsConstructor
public class LettuceConfiguration {

  private final RedisProperties redisProperties;

  @Primary
  @Bean
  public RedisConnectionFactory lettuceConnectionFactory() {
    RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(redisProperties.host(), redisProperties.port());
    return new LettuceConnectionFactory(redisConfiguration);
  }
}
