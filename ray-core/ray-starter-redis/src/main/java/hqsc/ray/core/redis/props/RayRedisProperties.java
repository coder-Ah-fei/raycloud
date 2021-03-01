package hqsc.ray.core.redis.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redis配置
 *
 * @author pangu
 */
@Getter
@Setter
@ConfigurationProperties(RayRedisProperties.PREFIX)
public class RayRedisProperties {
	/**
	 * 前缀
	 */
	public static final String PREFIX = "ray.lettuce.redis";
	/**
	 * 是否开启Lettuce
	 */
	private Boolean enable = true;
}
