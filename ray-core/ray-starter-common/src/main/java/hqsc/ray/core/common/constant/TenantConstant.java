package hqsc.ray.core.common.constant;

/**
 * 多租户常量
 * @author pangu
 * @date 2020-9-7
 */
public interface TenantConstant {

    /**
     * header 中租户ID
     */
    String RAY_TENANT_ID = "ray-tenant";

    /**
     * 租户id参数
     */
    String RAY_TENANT_ID_PARAM = "tenantId";

    /**
     * 租户ID
     */
    String TENANT_ID_DEFAULT = "1";

}
