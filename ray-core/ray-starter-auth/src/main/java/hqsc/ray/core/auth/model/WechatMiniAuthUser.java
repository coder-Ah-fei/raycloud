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
	
	/**
	 * 获取用户信息的方式，
	 * 微信小程序提供的两个获取用户信息的方式。
	 * 2021年4月13日小程序wx.getUserInfo(Object object)接口改版
	 * 详见文档https://developers.weixin.qq.com/community/develop/doc/000cacfa20ce88df04cb468bc52801?source=indexnew
	 * getUserInfo: 通过getUserInfo获取
	 * getUserProfile: 通过getUserProfile获取
	 */
	private String wechatGetUserInfoType;
}
