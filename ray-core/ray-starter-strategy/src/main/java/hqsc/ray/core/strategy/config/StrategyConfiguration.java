package hqsc.ray.core.strategy.config;

import hqsc.ray.core.strategy.service.BusinessHandler;
import hqsc.ray.core.strategy.service.BusinessHandlerChooser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 策略模式自动注入配置
 *
 * @author xuzhanfu
 * @date 2020-9-5
 */
@Configuration
public class StrategyConfiguration {

	@Bean
	public BusinessHandlerChooser businessHandlerChooser(List<BusinessHandler> businessHandlers) {
		BusinessHandlerChooser businessHandlerChooser = new BusinessHandlerChooser();
		businessHandlerChooser.setBusinessHandlerMap(businessHandlers);
		return businessHandlerChooser;
	}

}
