package hqsc.ray.system.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import hqsc.ray.core.common.constant.ProviderConstant;
import hqsc.ray.core.feign.constant.FeignConstant;

import java.util.List;

/**
 * 角色权限远程调用接口
 * @author pangu
 */
@FeignClient(value = FeignConstant.RAY_CLOUD_SYSTEM)
public interface ISysRolePermissionProvider {

    /**
     * 通过roleId获取权限列表
     * @param roleId　角色ID
     * @return List
     */
    @GetMapping(ProviderConstant.PROVIDER_ROLE_PERMISSION)
    List<String> getMenuIdByRoleId(@RequestParam("roleId") String roleId);
}
