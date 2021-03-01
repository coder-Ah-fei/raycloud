package hqsc.ray.core.auth.social;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.enums.scope.AuthWechatMpScope;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthDefaultRequest;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.GlobalAuthUtils;
import me.zhyd.oauth.utils.HttpUtils;
import me.zhyd.oauth.utils.UrlBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @program: raycloud
 * @description:
 * @author: Ryan Hsu
 * @create: 2021-01-09 00:50
 **/
public class AuthWeChatMiniRequest extends AuthDefaultRequest {
	
	public AuthWeChatMiniRequest(AuthConfig config) {
		super(config, AuthOtherSource.WECHAT_MINI);
	}
	
	public AuthWeChatMiniRequest(AuthConfig config, AuthStateCache authStateCache) {
		super(config, AuthOtherSource.WECHAT_MINI, authStateCache);
	}
	
	protected AuthToken getAccessToken(AuthCallback authCallback) {
		return this.getToken(this.accessTokenUrl(authCallback.getCode()), authCallback.getOauth_token(), authCallback.getAuth_code());
	}
	
	protected AuthUser getUserInfo(AuthToken authToken) {
		String openId = authToken.getOpenId();
		JSONObject object = JSONObject.parseObject(authToken.getAccessToken());
		String location = String.format("%s-%s-%s", object.getString("country"), object.getString("province"), object.getString("city"));
		if (object.containsKey("unionid")) {
			authToken.setUnionId(object.getString("unionid"));
		}
		
		return AuthUser.builder().rawUserInfo(object).username(object.getString("nickname")).nickname(object.getString("nickname")).avatar(object.getString("avatarUrl")).location(location).uuid(openId).gender(AuthUserGender.getWechatRealGender(object.getString("gender"))).token(authToken).source(this.source.toString()).build();
	}
	
	public AuthResponse refresh(AuthToken oldToken) {
		return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).data(this.getToken(this.refreshTokenUrl(oldToken.getRefreshToken()), "", "")).build();
	}
	
	private void checkResponse(JSONObject object) {
		if (object.containsKey("errcode")) {
			throw new AuthException(object.getIntValue("errcode"), object.getString("errmsg"));
		}
	}
	
	private AuthToken getToken(String accessTokenUrl, String encryptedData, String iv) {
		String response = (new HttpUtils(this.config.getHttpConfig())).get(accessTokenUrl);
		JSONObject accessTokenObject = JSONObject.parseObject(response);
		this.checkResponse(accessTokenObject);
		String unionId = "";
		String jsonStr = "";
		String sessionKey = accessTokenObject.getString("session_key");
		JSONObject json = decrypt(encryptedData, sessionKey, iv);
		if (json != null) {
			jsonStr = json.toString();
			if (json.containsKey("unionId")) {
				unionId = json.getString("unionId");
			}
		}
//        return AuthToken.builder().accessToken(jsonStr).openId(accessTokenObject.getString("openid")).unionId(unionId).build();
		return AuthToken.builder().accessToken(jsonStr).openId(accessTokenObject.getString("openid")).unionId(accessTokenObject.getString("openid")).build();
	}
	
	public String authorize(String state) {
		return UrlBuilder.fromBaseUrl(this.source.authorize()).queryParam("appid", this.config.getClientId()).queryParam("redirect_uri", GlobalAuthUtils.urlEncode(this.config.getRedirectUri())).queryParam("response_type", "code").queryParam("scope", this.getScopes(",", false, AuthScopeUtils.getDefaultScopes(AuthWechatMpScope.values()))).queryParam("state", this.getRealState(state).concat("#wechat_redirect")).build();
	}
	
	protected String accessTokenUrl(String code) {
		return UrlBuilder.fromBaseUrl(this.source.accessToken()).queryParam("appid", this.config.getClientId()).queryParam("secret", this.config.getClientSecret()).queryParam("js_code", code).queryParam("grant_type", "authorization_code").build();
	}
	
	protected String userInfoUrl(AuthToken authToken) {
		return UrlBuilder.fromBaseUrl(this.source.userInfo()).queryParam("access_token", authToken.getAccessToken()).queryParam("openid", authToken.getOpenId()).queryParam("lang", "zh_CN").build();
	}
	
	protected String refreshTokenUrl(String refreshToken) {
		return UrlBuilder.fromBaseUrl(this.source.refresh()).queryParam("appid", this.config.getClientId()).queryParam("grant_type", "refresh_token").queryParam("refresh_token", refreshToken).build();
	}
	
	private JSONObject decrypt(String encryptedData, String sessionKey, String iv) {
		// 被加密的数据
		byte[] dataByte = Base64.decode(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.decode(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.decode(iv);
		
		try {
			// 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + 1;
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, StandardCharsets.UTF_8);
				return JSONObject.parseObject(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
