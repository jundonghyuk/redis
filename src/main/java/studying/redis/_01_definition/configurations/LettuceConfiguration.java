package studying.redis._01_definition.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
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
