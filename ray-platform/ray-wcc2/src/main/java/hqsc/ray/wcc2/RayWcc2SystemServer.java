package hqsc.ray.wcc2;

import hqsc.ray.core.feign.annotation.EnableRayFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 管理系统后台启动类
 *
 * @author xzf
 */
@EnableRayFeign
@EnableJpaAuditing
@SpringBootApplication
public class RayWcc2SystemServer {
	public static void main(String[] args) {
		SpringApplication.run(RayWcc2SystemServer.class, args);
	}
}
