package hqsc.ray.core.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.zhyd.oauth.model.AuthToken;

/**
 * @author yang
 * @date 2021-03-29
 */
@Getter
@Setter
@Accessors(chain = true)
public class WechatMiniAuthToken extends AuthToken {
	
	/**
	 * 微信小程序登录时的session_key
	 */
	private String sessionKey;
	
}
