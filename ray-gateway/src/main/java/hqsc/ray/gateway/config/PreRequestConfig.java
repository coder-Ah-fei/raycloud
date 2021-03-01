package hqsc.ray.gateway.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import hqsc.ray.core.cloud.props.RayRequestProperties;
import hqsc.ray.core.cloud.props.RayApiProperties;

/**
 * 预请求配置
 *
 * @author pangu
 */
@Configuration
@EnableConfigurationProperties({RayRequestProperties.class, RayApiProperties.class})
public class PreRequestConfig {
}
