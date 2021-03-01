package hqsc.ray.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import hqsc.ray.core.feign.annotation.EnableRayFeign;

/**
 * 代码生成启动类
 *
 * @author xuzhanfu
 * @date 2019-10-09 15:06
 **/
@EnableRayFeign
@SpringBootApplication
public class RayCodeServer {
    public static void main(String[] args) {
        SpringApplication.run(RayCodeServer.class, args);
    }
}