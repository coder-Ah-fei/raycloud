package hqsc.ray.wcc.utils;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.redis.core.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
	
	
	private final String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	private final String msg_sec_check_url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=";
	private final String img_sec_check_url = "https://api.weixin.qq.com/wxa/img_sec_check?access_token=";
	
	/**
	 * 获取access_token
	 *
	 * @return
	 */
	public synchronized String getAccessToken() {
		String access_token = (String) redisService.get("base_access_token");
		if (StringUtil.isBlank(access_token)) {
			String url = token_url + "&appid=" + appid + "&secret=" + appsecret;
			Map map = HttpsUtil.get(url, Map.class);
			access_token = (String) map.get("access_token");
			redisService.set("base_access_token", access_token, 7150L);
		}
		return access_token;
	}
	
	
	/**
	 * 校验内容否合规
	 *
	 * @param content
	 * @return
	 */
	public boolean msgSecCheck(String content) {
		String url = msg_sec_check_url + getAccessToken();
		Map<String, String> map = new HashMap<>();
		map.put("content", content);
		String params = JSONUtil.toJsonStr(map);
		Map post = HttpsUtil.post(url, params, Map.class);
		if ((Integer) post.get("errcode") == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检验图片内容
	 *
	 * @param imgUrl
	 * @return
	 */
	public boolean imgSecCheck(String imgUrl) {
		String url = img_sec_check_url + getAccessToken();
		imgUrl = "https://image.baidu.com/search/detail?z=0&word=%E5%9F%8E%E5%B8%82%E5%BB%BA%E7%AD%91%E6%91%84%E5%BD%B1%E4%B8%93%E9%A2%98&hs=0&pn=0&spn=0&di=&pi=3977&tn=baiduimagedetail&is=&ie=utf-8&oe=utf-8&cs=1595072465%2C3644073269&os=&simid=&adpicid=0&lpn=0&fr=albumsdetail&fm=&ic=0&sme=&cg=&bdtype=&oriquery=&objurl=https%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D1595072465%2C3644073269%26fm%3D193%26f%3DGIF&fromurl=ipprf_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bev2_z%26e3Bv54AzdH3Fv6jwptejAzdH3Fb88cc0c0a&gsm=0&islist=&querylist=&album_tab=%E5%BB%BA%E7%AD%91&album_id=7";
		try {
			byte[] bytes = HttpsUtil.get(imgUrl);
			String uploadFile = HttpsUtil.uploadFile(url, bytes);
			System.out.println("--------------------------" + uploadFile);
//			JSON.parseObject(uploadFile,Map.class);
			Map map = JSON.parseObject(uploadFile, Map.class);
			if ((Integer) map.get("errcode") == 0) {
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean imgSecCheck(MultipartFile file) {
		String url = img_sec_check_url + getAccessToken();
		String uploadFile = HttpsUtil.uploadFile(url, file);
		System.out.println("--------------------------" + uploadFile);
		Map map = JSONUtil.toBean(uploadFile, Map.class);
		if ((Integer) map.get("errcode") == 0) {
			return true;
		}
		return false;
	}
}
