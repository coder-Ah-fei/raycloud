package hqsc.ray.component.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * Nacos路由工具类配置
 * @author pangu
 */
@Component
@Configuration
public class AttachmentConfig {


    @Value("${file.maxsize}")
    private String MAX_SIZE;

    @Value("${web.upload-path}")
    private String UPLOAD_PATH;

    public String getMaxSize() {
        return MAX_SIZE;
    }

    public String getUploadPath() {
        return UPLOAD_PATH;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES)); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.of(10, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
}
