package studying.redis._01_definition.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(
    prefix = "redis.chapters.chapter01",
    name = "enabled",
    havingValue = "true"
)
@Import({LettuceConfiguration.class, JedisConfiguration.class})
public class RedisConfiguration {

}
