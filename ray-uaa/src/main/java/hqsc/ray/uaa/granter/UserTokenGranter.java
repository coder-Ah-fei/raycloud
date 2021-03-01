package hqsc.ray.uaa.granter;

import com.alibaba.fastjson.JSON;
import com.xkcoding.justauth.AuthRequestFactory;
import hqsc.ray.core.common.constant.Oauth2Constant;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.redis.core.RedisService;
import hqsc.ray.uaa.social.SocialAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 小程序TokenGranter
 *
 * @author pangu
 * @since 2020-7-26
 */
@Slf4j
public class UserTokenGranter extends AbstractTokenGranter {
	private static final String GRANT_TYPE = "user-wechat";

	private final AuthenticationManager authenticationManager;

	private RedisService redisService;

	private AuthRequestFactory factory;

	public UserTokenGranter(AuthenticationManager authenticationManager,
                            AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                            OAuth2RequestFactory requestFactory, RedisService redisService, AuthRequestFactory factory) {
		this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.redisService = redisService;
		this.factory = factory;
	}

	protected UserTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
                               ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
		super(tokenServices, clientDetailsService, requestFactory, grantType);
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		String code = parameters.get("code");
		String encryptedData = parameters.get("encryptedData");
		String iv = parameters.get("iv");

		if (StringUtils.isBlank(code)||StringUtils.isBlank(encryptedData)||StringUtils.isBlank(iv)) {
			throw new UserDeniedAuthorizationException("未传入请求参数");
		}
		AuthRequest authRequest = factory.get("WECHAT_MINI");
		AuthCallback authCallback = AuthCallback.builder().code(code).state("state").oauth_token(encryptedData).auth_code(iv).build();
		AuthResponse response = authRequest.login(authCallback);
		log.info("【response】= {}", JSON.toJSON(response));
		AuthUser authUser = null;
		// 第三方登录成功
		if (response.getCode() == AuthResponseStatus.SUCCESS.getCode()) {
			authUser = (AuthUser) response.getData();
			authUser.setRemark(Oauth2Constant.USER_TYPE_USER);
		}
		log.error("authUser:{}", JSON.toJSON(authUser));

		String unionId=authUser.getToken().getUnionId();
		if(StringUtil.isBlank(unionId)){
			throw new UserDeniedAuthorizationException("获取不到UnionId");
		}

		Authentication userAuth = new SocialAuthenticationToken(authUser);
		((AbstractAuthenticationToken) userAuth).setDetails(parameters);
		try {
			userAuth = authenticationManager.authenticate(userAuth);
		} catch (AccountStatusException | BadCredentialsException ase) {
			//covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
			throw new InvalidGrantException(ase.getMessage());
		}
		// If the username/password are wrong the spec says we should send 400/invalid grant

		if (userAuth == null || !userAuth.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user: " + authUser);
		}

		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}
}
