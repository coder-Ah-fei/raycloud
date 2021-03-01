package hqsc.ray.core.feign.config;

import feign.Feign;
import hqsc.ray.core.feign.endpoint.FeignClientEndpoint;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.RayFeignClientsRegistrar;
import org.springframework.cloud.openfeign.RayHystrixTargeter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * Feign配置类
 *
 * @author xuzhanfu
 */
@Configuration
@ConditionalOnClass(Feign.class)
@Import(RayFeignClientsRegistrar.class)
@AutoConfigureAfter(EnableFeignClients.class)
public class RayFeignAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnAvailableEndpoint
    public FeignClientEndpoint feignClientEndpoint(ApplicationContext context) {
        return new FeignClientEndpoint(context);
    }

    @Bean
    @Primary
    public RayHystrixTargeter rayFeignTargeter() {
        return new RayHystrixTargeter();
    }
}
