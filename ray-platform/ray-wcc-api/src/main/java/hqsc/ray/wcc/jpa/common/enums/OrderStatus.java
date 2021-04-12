package hqsc.ray.wcc.jpa.common.enums;

/**
 * 订单状态
 *
 * @author yang
 */
public enum OrderStatus {
	
	/**
	 * 已下单
	 */
	ordered("已下单"),
	
	/**
	 * 已取消
	 */
	revoke("已取消"),
	
	/**
	 * 已付款
	 */
	pay("已付款"),
	
	/**
	 * 已发货
	 */
	send("已发货"),
	
	/**
	 * 已收货
	 */
	receive("已收货"),
	
	/**
	 * 退货申请
	 */
	retreat_apply("退单申请"),
	
	/**
	 * 退货驳回
	 */
	reject_apply("退单驳回"),
	
	/**
	 * 已退货
	 */
	retreat("已退货"),
	
	/**
	 * 已退款
	 */
	refund("已退款"),
	/**
	 * 退款异常
	 */
	refund_fail("退款异常"),
	
	/**
	 * 已完成
	 */
	finish("已完成"),
	
	/**
	 * 扫码订单冲正操作有订单状态改为已作废
	 */
	invalid("已作废");
	
	private String text;
	
	private OrderStatus(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
