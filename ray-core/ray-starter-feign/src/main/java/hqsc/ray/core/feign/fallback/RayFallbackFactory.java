package hqsc.ray.core.feign.fallback;

import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 默认fallback，减少必要的编写fallback类
 * @param <T>
 */
@AllArgsConstructor
public class RayFallbackFactory<T> implements FallbackFactory<T> {
    private final Target<T> target;

    @Override
    @SuppressWarnings("unchecked")
    public T create(Throwable cause) {
        final Class<T> targetType = target.type();
        final String targetName = target.name();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetType);
        enhancer.setUseCache(true);
        enhancer.setCallback(new RayFeignFallback<>(targetType, targetName, cause));
        return (T) enhancer.create();
    }
}
