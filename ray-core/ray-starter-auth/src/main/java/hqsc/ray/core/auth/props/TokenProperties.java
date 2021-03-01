package hqsc.ray.core.auth.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Token属性
 *
 * @author pangu
 */
@Getter
@Setter
@ConfigurationProperties(TokenProperties.PREFIX)
public class TokenProperties {

	/**
	 * 前缀
	 */
	public static final String PREFIX = "ray.token.auth";

	/**
	 * 是否开启token验证
	 */
	private Boolean enable = Boolean.TRUE;
}
