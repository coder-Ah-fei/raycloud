package hqsc.ray.core.cloud.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import hqsc.ray.core.cloud.filter.TenantContextHolderFilter;
import hqsc.ray.core.cloud.filter.TraceFilter;
import hqsc.ray.core.cloud.props.RayRequestProperties;

/**
 * 请求配置，包括tracId和其他网络请求
 * @author pangu
 */
@Configuration
@EnableConfigurationProperties(RayRequestProperties.class)
public class RequestConfiguration {

    @Bean
    public TenantContextHolderFilter tenantContextHolderFilter() {
        return new TenantContextHolderFilter();
    }

    @Bean
    public TraceFilter traceFilter() {
        return new TraceFilter();
    }

}
