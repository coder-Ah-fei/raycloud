package hqsc.ray.system.mapper;

import hqsc.ray.system.entity.SysDepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hqsc.ray.system.vo.SysDepartVO;

import java.util.List;

/**
 * <p>
 * 组织机构表 Mapper 接口
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
public interface SysDepartMapper extends BaseMapper<SysDepart> {

    List<SysDepartVO> tree();

}
