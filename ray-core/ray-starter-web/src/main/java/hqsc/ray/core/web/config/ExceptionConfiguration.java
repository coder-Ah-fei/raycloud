package hqsc.ray.core.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import hqsc.ray.core.common.factory.YamlPropertySourceFactory;

/**
 * 统一异常处理配置
 * @author xuzhanfu
 */
@Configuration
@ComponentScan(value="hqsc.ray.core.web.handler")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:ray-error.yml")
public class ExceptionConfiguration {
}