package hqsc.ray.core.retrofit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import hqsc.ray.core.common.factory.YamlPropertySourceFactory;
import hqsc.ray.core.retrofit.interceptor.SignInterceptor;

/**
 * Retrofit配置类
 *
 * @author pangu
 */
@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:ray-retrofit.yml")
public class RetrofitConfiguration {

	@Bean
	public SignInterceptor signInterceptor() {
		return new SignInterceptor();
	}

}
