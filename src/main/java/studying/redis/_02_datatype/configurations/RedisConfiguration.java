package studying.redis._02_datatype.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(
    prefix = "redis.chapters.chapter02",
    name = "enabled",
    havingValue = "true"
)
@Import({LettuceConfiguration.class})
public class RedisConfiguration {

}
