/*
 * Copyright 2019-2028 Beijing Daotiandi Technology Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Author: xuzhanfu (7333791@qq.com)
 */
package hqsc.ray.component;

import hqsc.ray.core.feign.annotation.EnableRayFeign;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.slf4j.Logger;


/**
 * 系统组件启动类
 * @author pangu
 */
@EnableRayFeign
@SpringBootApplication
public class RayComponentServer extends WebMvcConfigurerAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(RayComponentServer.class);

    @Value("${web.upload-path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/**").addResourceLocations(
                "file:"+uploadPath);
        LOGGER.info("自定义静态资源目录、此处功能用于文件映射");
    }

    public static void main(String[] args) {
        SpringApplication.run(RayComponentServer.class, args);
    }
}
