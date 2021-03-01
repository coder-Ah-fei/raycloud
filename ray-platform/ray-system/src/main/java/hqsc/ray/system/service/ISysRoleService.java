package hqsc.ray.system.service;

import hqsc.ray.system.entity.SysRole;
import hqsc.ray.system.poi.SysRolePOI;
import com.baomidou.mybatisplus.extension.service.IService;
import hqsc.ray.system.vo.SysRoleVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
public interface ISysRoleService extends IService<SysRole> {

    List<SysRoleVO> tree();

    List<SysRole> listSearch(Map<String, String> search);

    List<String> getPermission(String id);

    List<SysRolePOI> export();

}
