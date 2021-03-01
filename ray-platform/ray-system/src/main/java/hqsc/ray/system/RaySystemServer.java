package hqsc.ray.system;

import hqsc.ray.core.feign.annotation.EnableRayFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 管理系统后台启动类
 *
 * @author xzf
 */
@EnableRayFeign
@SpringBootApplication
@ComponentScan({"hqsc.ray.wcc.service", "hqsc.ray.system"})
public class RaySystemServer {
	public static void main(String[] args) {
		SpringApplication.run(RaySystemServer.class, args);
	}
}
