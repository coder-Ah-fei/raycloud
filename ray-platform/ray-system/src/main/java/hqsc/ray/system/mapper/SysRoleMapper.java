package hqsc.ray.system.mapper;

import hqsc.ray.system.entity.SysRole;
import hqsc.ray.system.vo.SysRoleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRoleVO> tree();

}
