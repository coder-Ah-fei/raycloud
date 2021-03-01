package hqsc.ray.wcc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import hqsc.ray.core.feign.annotation.EnableRayFeign;

/**
 * 管理系统后台启动类
 * @author xzf
 */
@EnableRayFeign
@SpringBootApplication
public class RayWccSystemServer {
    public static void main(String[] args) {
        SpringApplication.run(RayWccSystemServer.class, args);
    }
}
