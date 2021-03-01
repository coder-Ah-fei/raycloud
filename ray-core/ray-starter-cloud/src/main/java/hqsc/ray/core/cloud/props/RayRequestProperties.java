package hqsc.ray.core.cloud.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 链路追踪配置
 * @author xuzhanfu
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("ray.request")
public class RayRequestProperties {

    /**
     * 是否开启日志链路追踪
     */
    private Boolean trace = false;

    /**
     * 是否启用获取IP地址
     */
    private Boolean ip = false;

    /**
     * 是否启用增强模式
     */
    private Boolean enhance = false;
}
