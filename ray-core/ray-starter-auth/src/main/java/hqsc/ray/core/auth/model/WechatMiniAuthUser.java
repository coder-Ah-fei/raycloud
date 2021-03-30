package hqsc.ray.core.auth.model;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-03-29
 */

import me.zhyd.oauth.model.AuthUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class WechatMiniAuthUser extends AuthUser {
	/**
	 * 微信小程序登录时的session_key
	 */
	private String sessionKey;
}
