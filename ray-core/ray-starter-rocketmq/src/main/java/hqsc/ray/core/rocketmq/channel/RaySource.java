package hqsc.ray.core.rocketmq.channel;

import hqsc.ray.core.rocketmq.constant.MessageConstant;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 生产者Channel
 *
 * @author xuzhanfu
 */
public interface RaySource {

	/**
	 * 短消息通道
	 *
	 * @return MessageChannel
	 */
	@Output(MessageConstant.SMS_MESSAGE_OUTPUT)
	MessageChannel smsOutput();

	/**
	 * 邮件通道
	 *
	 * @return MessageChannel
	 */
	@Output(MessageConstant.EMAIL_MESSAGE_OUTPUT)
	MessageChannel emailOutput();

	/**
	 * 订单通道
	 *
	 * @return MessageChannel
	 */
	@Output(MessageConstant.ORDER_MESSAGE_OUTPUT)
	MessageChannel orderOutput();
}
