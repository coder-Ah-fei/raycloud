package hqsc.ray.core.common.util;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author
 */
public class AesHelper {
	
	/**
	 * 微信小程序 开放数据解密
	 * AES解密（Base64）
	 *
	 * @param encryptedData 已加密的数据
	 * @param sessionKey    解密密钥
	 * @param iv            IV偏移量
	 * @return
	 * @throws Exception
	 */
	public static String decryptForWeChatApplet(String encryptedData, String sessionKey, String iv) throws Exception {
		Base64Utils.decode(encryptedData.getBytes());
		byte[] decryptBytes = Base64Utils.decode(encryptedData.getBytes());
		byte[] keyBytes = Base64Utils.decode(sessionKey.getBytes());
		byte[] ivBytes = Base64Utils.decode(iv.getBytes());
		return new String(decryptByAesBytes(decryptBytes, keyBytes, ivBytes));
	}
	
	/**
	 * AES解密
	 *
	 * @param decryptedBytes 待解密的字节数组
	 * @param keyBytes       解密密钥字节数组
	 * @param ivBytes        IV初始化向量字节数组
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByAesBytes(byte[] decryptedBytes, byte[] keyBytes, byte[] ivBytes) throws Exception {
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] outputBytes = cipher.doFinal(decryptedBytes);
		
		return outputBytes;
	}
	
}
