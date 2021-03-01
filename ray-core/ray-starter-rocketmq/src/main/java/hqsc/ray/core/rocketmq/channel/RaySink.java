package hqsc.ray.core.rocketmq.channel;

import hqsc.ray.core.rocketmq.constant.MessageConstant;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消费者Channel
 *
 * @author pangu
 */
public interface RaySink {

	String SMS_MESSAGE_INPUT = MessageConstant.SMS_MESSAGE_INPUT;

	String EMAIL_MESSAGE_INPUT = MessageConstant.EMAIL_MESSAGE_INPUT;

	String ORDER_MESSAGE_INPUT = MessageConstant.ORDER_MESSAGE_INPUT;

	/**
	 * 短消息消费者
	 *
	 * @return SubscribableChannel
	 */
	@Input(SMS_MESSAGE_INPUT)
	SubscribableChannel smsInput();

	/**
	 * 邮件消费者
	 *
	 * @return SubscribableChannel
	 */
	@Input(EMAIL_MESSAGE_INPUT)
	SubscribableChannel emailInput();

	/**
	 * 订单消费者
	 *
	 * @return SubscribableChannel
	 */
	@Input(ORDER_MESSAGE_INPUT)
	SubscribableChannel orderInput();
}
