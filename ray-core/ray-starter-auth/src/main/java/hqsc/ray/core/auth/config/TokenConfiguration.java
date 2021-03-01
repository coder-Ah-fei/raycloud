package hqsc.ray.core.auth.config;

import hqsc.ray.core.auth.props.TokenProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Token配置
 *
 * @author pangu
 */
@Configuration
@ComponentScan(value = "hqsc.ray.core.auth")
@EnableConfigurationProperties(TokenProperties.class)
@ConditionalOnProperty(value = TokenProperties.PREFIX + ".enabled", havingValue = "true", matchIfMissing = true)
public class TokenConfiguration {

}
