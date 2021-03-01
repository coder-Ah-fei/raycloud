package hqsc.ray.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import hqsc.ray.core.rocketmq.channel.RaySource;

/**
 * 消息中心生产者启动类
 *
 * @author pangu
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableBinding({RaySource.class})
public class RayMessageProducerServer {
	public static void main(String[] args) {
		SpringApplication.run(RayMessageProducerServer.class, args);
	}
}
