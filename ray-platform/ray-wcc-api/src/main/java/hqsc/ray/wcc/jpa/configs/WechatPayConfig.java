package hqsc.ray.wcc.jpa.configs;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

/**
 * 微信支付配置
 *
 * @author yang
 * @date 2021-04-09
 */
@Configuration
public class WechatPayConfig {
	@Value("${wechat.mchid}")
	private String mchid;
	@Value("${wechat.merchant_serial_number}")
	private String merchantSerialNumber;
	@Value("${wechat.api_v3_key}")
	private String apiV3Key;
	@Value("${wechat.merchant_private_key_path}")
	private String merchantPrivateKeyPath;
	
	@Bean
	public PrivateKey wechatPrivateKey() {
		File apiclientKeyFile = new File(merchantPrivateKeyPath);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(apiclientKeyFile);
			return PemUtil.loadPrivateKey(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		throw new RuntimeException("注入微信商户私钥失败");
	}
	
	
	@Bean
	public HttpClient wechatPayHttpClient(PrivateKey wechatPrivateKey) {
		//不需要传入微信支付证书，将会自动更新
		AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(new WechatPay2Credentials(mchid, new PrivateKeySigner(merchantSerialNumber, wechatPrivateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));
		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
				.withMerchant(mchid, merchantSerialNumber, wechatPrivateKey)
				.withValidator(new WechatPay2Validator(verifier));
		return builder.build();
	}
}
