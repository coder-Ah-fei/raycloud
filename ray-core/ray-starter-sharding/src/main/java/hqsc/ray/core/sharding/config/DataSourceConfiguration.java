package hqsc.ray.core.sharding.config;

import hqsc.ray.core.common.factory.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:ray-sharding-db.yml")
public class DataSourceConfiguration {
}
