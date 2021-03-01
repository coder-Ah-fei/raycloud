package hqsc.ray.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Admin启动类
 *
 * @author pangu
 */
@EnableAdminServer
@SpringBootApplication
public class RayAdminServer {
	public static void main(String[] args) {
		SpringApplication.run(RayAdminServer.class, args);
	}
}
