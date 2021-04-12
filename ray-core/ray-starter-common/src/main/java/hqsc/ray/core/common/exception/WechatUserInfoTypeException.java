package hqsc.ray.core.common.exception;

/**
 * 获取微信用户信息的调用方式不正确
 *
 * @author xuzhanfu
 */
public class WechatUserInfoTypeException extends RuntimeException {
	
	private static final long serialVersionUID = -109638013567525177L;
	
	public WechatUserInfoTypeException(String message) {
		super(message);
	}
}
