package hqsc.ray.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hqsc.ray.system.entity.SysMenu;
import hqsc.ray.system.poi.SysMenuPOI;
import hqsc.ray.system.vo.SysMenuVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author xuzf
 * @since 2020-06-18
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenuVO> routes(String roleId);

    List<SysMenu> searchList(Map<String, Object> search);

    boolean saveAll(SysMenu sysMenu);

    boolean status(String ids, String status);

    List<SysMenuPOI> export();

}
