package hqsc.ray.core.seata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import hqsc.ray.core.common.factory.YamlPropertySourceFactory;

/**
 * Seata配置
 *
 * @author pangu
 * @since 2.0.8
 */
@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:ray-seata.yml")
public class SeataConfiguration {
}
