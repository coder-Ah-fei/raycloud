package hqsc.ray.wcc.jpa.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hqsc.ray.wcc.jpa.dto.wechatPay.UnifiedOrderDto;
import hqsc.ray.wcc.jpa.form.wechatPay.UnifiedOrderForm;
import hqsc.ray.wcc.jpa.service.WechatPayService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.PrivateKey;

/**
 * 微信商户平台支付实现类
 *
 * @author yang
 * @date 2021-04-08
 */
@Service
@RequiredArgsConstructor
public class WechatPayServiceImpl implements WechatPayService {
	
	private final PrivateKey privateKey;
	private final HttpClient wechatPayHttpClient;
	
	@Value("${wechat.appid}")
	private String appid;
	@Value("${wechat.mchid}")
	private String mchid;
	@Value("${wechat.notify_url}")
	private String notifyUrl;
	
	private final String unifiedOrderUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
	
	
	/**
	 * 微信统一下单
	 *
	 * @param unifiedOrderForm
	 * @return
	 */
	@Override
	public UnifiedOrderDto unifiedOrder(UnifiedOrderForm unifiedOrderForm) {
		unifiedOrderForm.setAppid(appid);
		unifiedOrderForm.setMchid(mchid);
		unifiedOrderForm.setNotify_url(notifyUrl);
		
		String params = JSONObject.toJSONString(unifiedOrderForm, true);
		System.out.println("params====" + params);
		HttpPost httpPost = new HttpPost(unifiedOrderUrl);
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-type", "application/json; charset=utf-8");
		httpPost.setEntity(new StringEntity(params, "UTF-8"));
		HttpResponse response = null;
		try {
			response = wechatPayHttpClient.execute(httpPost);
			String bodyAsString = EntityUtils.toString(response.getEntity());
			System.out.println("---------------------------");
			System.out.println(bodyAsString);
			System.out.println("---------------------------");
			
			UnifiedOrderDto unifiedOrderDto = JSON.parseObject(bodyAsString, UnifiedOrderDto.class);
			return unifiedOrderDto;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
