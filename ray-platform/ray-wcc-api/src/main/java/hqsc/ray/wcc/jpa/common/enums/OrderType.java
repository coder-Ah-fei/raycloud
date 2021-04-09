package hqsc.ray.wcc.jpa.common.enums;

/**
 * 订单类型
 *
 * @author yang
 */
public enum OrderType {
	
	/**
	 *
	 */
	OPEN_MEMBER_ORDER("开通会员订单"),
	EXAM_ORDER("考试报名订单"),
	COURSE_ORDER("购买课程订单");
	
	private String text;
	
	OrderType(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
