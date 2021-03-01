package hqsc.ray.message.service.impl;

import lombok.AllArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import hqsc.ray.core.rocketmq.channel.RaySource;
import hqsc.ray.message.service.ISmsService;

/**
 * 发送短信实现类
 *
 * @author xuzhanfu
 */
@Service
@AllArgsConstructor
public class SmsServiceImpl implements ISmsService {

	private final RaySource source;

	private final RocketMQTemplate rocketMQTemplate;

	@Override
	public void sendSms(String message) {
		source.smsOutput().send(MessageBuilder.withPayload(message).build());
	}
}
