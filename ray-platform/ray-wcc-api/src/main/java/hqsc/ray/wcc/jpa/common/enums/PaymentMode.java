package hqsc.ray.wcc.jpa.common.enums;

/**
 * 会员续费模式
 *
 * @author yang
 */
public enum PaymentMode {
	
	/**
	 *
	 */
	Monthly("连续包月"),
	Quarterly("连续包季"),
	Yearly("连续包年");
	
	private String text;
	
	PaymentMode(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
