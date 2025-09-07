package studying.redis._01_definition.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
    prefix = "redis.chapters.chapter01",
    name = "type",
    havingValue = "lettuce",
    matchIfMissing = true
)
public class LettuceConfiguration {

}
