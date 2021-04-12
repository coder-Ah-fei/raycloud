package hqsc.ray.wcc.jpa.common.enums;

/**
 * 支付方式
 *
 * @author yang
 */
public enum PayMode {
	
	/**
	 *
	 */
	WECHAT_PAY("微信支付");
	
	private String text;
	
	PayMode(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
