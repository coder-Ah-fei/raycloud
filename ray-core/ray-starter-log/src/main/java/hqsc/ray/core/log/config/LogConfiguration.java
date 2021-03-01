package hqsc.ray.core.log.config;

import hqsc.ray.core.log.feign.ICommonLogProvider;
import hqsc.ray.core.log.feign.ISysLogProvider;
import hqsc.ray.core.log.props.LogProperties;
import hqsc.ray.core.log.props.LogType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import hqsc.ray.core.log.aspect.LogAspect;
import hqsc.ray.core.log.event.LogListener;

/**
 * 日志配置中心
 * @author pangu
 */
@EnableAsync
@Configuration
@RequiredArgsConstructor
@ConditionalOnWebApplication
@EnableConfigurationProperties(value = LogProperties.class)
public class LogConfiguration {

    private final ISysLogProvider sysLogProvider;
    private final ICommonLogProvider commonLogProvider;

    private final LogProperties logProperties;

    @Bean
    public LogListener sysLogListener() {
        if (logProperties.getLogType().equals(LogType.KAFKA)) {
            return new LogListener(commonLogProvider, logProperties);
        }
        return new LogListener(sysLogProvider, logProperties);

    }

    @Bean
    public LogAspect logAspect(ApplicationContext applicationContext){
        return new LogAspect(applicationContext);
    }
}
