package hqsc.ray.uaa.social;

import hqsc.ray.core.auth.model.WechatMiniAuthUser;
import hqsc.ray.core.common.constant.Oauth2Constant;
import hqsc.ray.core.common.exception.WechatUserInfoTypeException;
import hqsc.ray.core.security.userdetails.RayUserDetailsService;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 * 社交登录验证提供者
 *
 * @author pangu
 */
@AllArgsConstructor
public class SocialAuthenticationProvider implements AuthenticationProvider {
	
	private final RayUserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;
		AuthUser authUser = (AuthUser) authenticationToken.getPrincipal();
		String nickName = (String) authUser.getRawUserInfo().get("nickName");
		/**
		 * 调用 {@link UserDetailsService}api.weixin.qq.com/sns/jscode2session
		 */
		UserDetails user = null;
		if (Oauth2Constant.USER_TYPE_USER.equals(authUser.getRemark())) {
			user = userDetailsService.loadWUserBySocial(authUser.getToken().getUnionId());
			if (user == null) {
				
				if (Oauth2Constant.GET_USER_INFO_TYPE_INFO.equals(((WechatMiniAuthUser) authUser).getWechatGetUserInfoType())) {
					throw new WechatUserInfoTypeException("当前用户不存在，请使用getUserProfile接口获取用户的详细信息");
				}
				
				//补充将网络图片保存到本地得方法
				Long avatarId = userDetailsService.createAtt(authUser.getAvatar());
				userDetailsService.createWccUser(authUser.getToken().getUnionId(), nickName
						, avatarId, authUser.getAvatar(), authUser.getGender().getCode());
				//最后再获取一下
				user = userDetailsService.loadWUserBySocial(authUser.getToken().getUnionId());
			} else {
			}
		} else {
			user = userDetailsService.loadUserBySocial(authUser.getUuid());
		}
		
		if (Objects.isNull(user)) {
			throw new InternalAuthenticationServiceException("社交登录错误");
		}
		
		SocialAuthenticationToken authenticationResult = new SocialAuthenticationToken(user, user.getAuthorities());
		
		authenticationResult.setDetails(authenticationToken.getDetails());
		
		return authenticationResult;
		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return SocialAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
}
