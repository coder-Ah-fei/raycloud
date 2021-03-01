package hqsc.ray.system.service;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.constant.SystemConstant;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.system.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author xuzf
 * @since 2020-07-01
 */
public interface ISysDictService extends IService<SysDict> {

    @Cached(name= SystemConstant.SYS_DICT_CACHE, expire = 3600)
    Result<String> getValue(String code, String dictKey);

    @Cached(name= SystemConstant.SYS_DICT_CACHE, key="#code", expire = 3600)
    Result<List<SysDict>> getList(String code);

    IPage<SysDict> listPage(Page page, Search search);

}
