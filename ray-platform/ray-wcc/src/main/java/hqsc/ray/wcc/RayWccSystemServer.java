package hqsc.ray.wcc;

import hqsc.ray.core.feign.annotation.EnableRayFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 管理系统后台启动类
 *
 * @author xzf
 */
@EnableJpaAuditing
@EnableRayFeign
@SpringBootApplication
public class RayWccSystemServer {
	public static void main(String[] args) {
		SpringApplication.run(RayWccSystemServer.class, args);
	}
}
