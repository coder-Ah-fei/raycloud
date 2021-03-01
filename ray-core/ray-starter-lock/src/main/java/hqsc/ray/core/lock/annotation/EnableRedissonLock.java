package hqsc.ray.core.lock.annotation;

import hqsc.ray.core.lock.config.RedissonConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启Redisson注解支持
 *
 * @author pangu
 * @date 2020-10-22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(RedissonConfiguration.class)
public @interface EnableRedissonLock {
}
