package hqsc.ray.core.feign.constant;

import lombok.experimental.UtilityClass;

/**
 * Feign常量类
 * @author xuzhanfu
 * @Date 2020-7-1
 */
@UtilityClass
public class FeignConstant {

    /**
     * 网关
     */
    public final String RAY_CLOUD_GATEWAY = "ray-gateway";

    /**
     * 系统服务
     */
    public final String RAY_CLOUD_SYSTEM = "ray-system";

    /**
     * 系统服务
     */
    public final String RAY_CLOUD_WCC = "ray-wcc";

    /**
     * 认证服务
     */
    public final String RAY_CLOUD_UAA = "ray-uaa";

    /**
     * 消息生产者
     */
    public final String RAY_CLOUD_LOG_PRODUCER = "ray-log-producer";

    /**
     * 附件
     */
    public final String RAY_CLOUD_COMPONENT = "ray-component";
}
