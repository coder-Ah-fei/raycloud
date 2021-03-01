package hqsc.ray.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import hqsc.ray.core.common.constant.RayConstant;
import hqsc.ray.core.gray.support.RibbonFilterContextHolder;

/**
 * 灰度环境过滤器
 *
 * @author pangu
 */
public class GrayFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        /**
         * 值越大，优先级越低
         */
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求头header中的version版本号
        String version = exchange.getRequest().getHeaders().getFirst(RayConstant.VERSION);
        RibbonFilterContextHolder.getCurrentContext().add(RayConstant.VERSION, version);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            RibbonFilterContextHolder.getCurrentContext().remove(RayConstant.VERSION);
        }));
    }
}
