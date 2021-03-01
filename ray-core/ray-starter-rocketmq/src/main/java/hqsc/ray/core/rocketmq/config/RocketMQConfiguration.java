package hqsc.ray.core.rocketmq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import hqsc.ray.core.common.factory.YamlPropertySourceFactory;

/**
 * RocketMQ配置
 *
 * @author pangu
 */
@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:ray-rocketmq.yml")
public class RocketMQConfiguration {
}
