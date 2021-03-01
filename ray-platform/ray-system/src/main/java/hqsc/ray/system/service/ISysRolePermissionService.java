package hqsc.ray.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hqsc.ray.system.entity.SysRolePermission;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author xuzf
 * @since 2020-07-02
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    List<String> getMenuIdByRoleId(String roleId);

}
