package hqsc.ray.core.rocketmq.constant;

import hqsc.ray.core.common.util.StringPool;

/**
 * 消息中心常量
 *
 * @author pangu
 */
public class MessageConstant {

	/**
	 * 生产者服务名称
	 */
	public static final String MESSAGE_PRODUCER_SERVICE = "ray-message-producer";

	/**
	 * 消费者服务名称
	 */
	public static final String MESSAGE_CONSUMER_SERVICE = "ray-message-consumer";


	/**
	 * 短信消息
	 */
	public static final String SMS_MESSAGE = "sms";

	/**
	 * 邮件消息
	 */
	public static final String EMAIL_MESSAGE = "email";

	/**
	 * 订单消息
	 */
	public static final String ORDER_MESSAGE = "order";

	/**
	 * 生产者标识
	 */
	public static final String OUTPUT = "output";

	/**
	 * 消费者标识
	 */
	public static final String INPUT = "input";

	/**
	 * 消息生产者
	 */
	public static final String SMS_MESSAGE_OUTPUT = SMS_MESSAGE + StringPool.DASH + OUTPUT;

	/**
	 * 邮件生产者
	 */
	public static final String EMAIL_MESSAGE_OUTPUT = EMAIL_MESSAGE + StringPool.DASH + OUTPUT;

	/**
	 * 订单生产者
	 */
	public static final String ORDER_MESSAGE_OUTPUT = ORDER_MESSAGE + StringPool.DASH + OUTPUT;

	/**
	 * 短信消费者
	 */
	public static final String SMS_MESSAGE_INPUT = SMS_MESSAGE + StringPool.DASH + INPUT;

	/**
	 * 邮件消费者
	 */
	public static final String EMAIL_MESSAGE_INPUT = EMAIL_MESSAGE + StringPool.DASH + INPUT;

	/**
	 * 订单消费者
	 */
	public static final String ORDER_MESSAGE_INPUT = ORDER_MESSAGE + StringPool.DASH + INPUT;

	/**
	 * 订单组
	 */
	public static final String ORDER_BINDER_GROUP = ORDER_MESSAGE + StringPool.DASH + "binder-group";


}
