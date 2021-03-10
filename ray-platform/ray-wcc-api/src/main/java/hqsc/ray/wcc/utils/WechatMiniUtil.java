package hqsc.ray.wcc.utils;

import hqsc.ray.core.redis.core.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-03-10
 */
@Component
@RequiredArgsConstructor
public class WechatMiniUtil {
	private final RedisService redisService;
	@Value("${wechat.appid}")
	private String appid;
	@Value("${wechat.appsecret}")
	private String appsecret;
	
	public String getAccessToken() {
		
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret;
		System.out.println(url);
		Map map = HttpsUtil.get(url, Map.class);
		return null;
	}
	
}
