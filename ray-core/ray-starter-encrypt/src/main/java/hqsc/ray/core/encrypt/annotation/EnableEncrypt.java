package hqsc.ray.core.encrypt.annotation;

import org.springframework.context.annotation.Import;
import hqsc.ray.core.encrypt.config.EncryptConfiguration;
import hqsc.ray.core.encrypt.config.WebConfiguration;

import java.lang.annotation.*;

/**
 * Enable encrypt of the Spring Application Context
 * 支持res和rsa的加密方式
 *
 * @author gaoyang pangu
 * @since 2.0.8
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EncryptConfiguration.class, WebConfiguration.class})
public @interface EnableEncrypt {
}
