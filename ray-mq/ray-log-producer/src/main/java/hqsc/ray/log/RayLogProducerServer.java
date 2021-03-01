package hqsc.ray.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import hqsc.ray.core.feign.annotation.EnableRayFeign;
import hqsc.ray.core.kafka.channel.LogChannel;

/**
 * 日志消息生产者启动类
 * @author pangu
 */
@EnableRayFeign
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableBinding(LogChannel.class)
public class RayLogProducerServer {

    public static void main(String[] args) {
        SpringApplication.run(RayLogProducerServer.class, args);
    }

}
