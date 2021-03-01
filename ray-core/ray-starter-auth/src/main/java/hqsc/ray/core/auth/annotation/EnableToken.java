package hqsc.ray.core.auth.annotation;

import java.lang.annotation.*;

/**
 * 启用Token验证
 *
 * @author pangu
 */
@Deprecated
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableToken {
}
