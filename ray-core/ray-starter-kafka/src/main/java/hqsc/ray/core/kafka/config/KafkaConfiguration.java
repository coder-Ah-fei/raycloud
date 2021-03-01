package hqsc.ray.core.kafka.config;

import hqsc.ray.core.common.factory.YamlPropertySourceFactory;
import org.springframework.context.annotation.PropertySource;

@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:ray-kafka.yml")
public class KafkaConfiguration {
}
