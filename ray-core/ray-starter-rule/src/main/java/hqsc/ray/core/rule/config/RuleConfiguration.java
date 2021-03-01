package hqsc.ray.core.rule.config;

import hqsc.ray.core.rule.service.IRuleCacheService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import hqsc.ray.core.rule.service.impl.RuleCacheServiceImpl;

/**
 * 规则配置
 * @author pangu
 */
@Configuration
public class RuleConfiguration {

    @Bean
    public IRuleCacheService ruleCacheService() {
        return new RuleCacheServiceImpl();
    }
}
