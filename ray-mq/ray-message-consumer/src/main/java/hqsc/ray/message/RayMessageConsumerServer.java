package hqsc.ray.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import hqsc.ray.core.rocketmq.channel.RaySink;

/**
 * 消息中心消费者启动类
 *
 * @author pangu
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableBinding({RaySink.class})
public class RayMessageConsumerServer {
	public static void main(String[] args) {
		SpringApplication.run(RayMessageConsumerServer.class, args);
	}
}
