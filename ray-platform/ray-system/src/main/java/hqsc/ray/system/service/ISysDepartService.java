package hqsc.ray.system.service;

import hqsc.ray.system.entity.SysDepart;
import hqsc.ray.system.poi.SysDepartPOI;
import com.baomidou.mybatisplus.extension.service.IService;
import hqsc.ray.system.vo.SysDepartVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构表 服务类
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
public interface ISysDepartService extends IService<SysDepart> {

    //@Cached(name="sysDepartService.tree", expire = 3600)
    List<SysDepartVO> tree();

    List<SysDepartVO> searchList(Map<String, Object> search);

    List<SysDepartPOI> export();

}
