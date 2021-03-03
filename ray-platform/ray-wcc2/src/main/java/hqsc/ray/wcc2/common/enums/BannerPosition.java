package hqsc.ray.wcc2.common.enums;


/**
 * banner图所在位置枚举
 *
 * @author yang
 */
public enum BannerPosition {
	
	/**
	 *
	 */
	XUE_JIAO_CHENG("学教程"),
	JU_YOU_XUAN("聚优选");
	
	private String text;
	
	BannerPosition(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
