package hqsc.ray.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.system.entity.SysUser;
import hqsc.ray.system.poi.SysUserPOI;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xuzf
 * @since 2020-06-18
 */
public interface ISysUserService extends IService<SysUser> {

    boolean status(String ids, String status);

    /**
     * 分页业务方法
     * @param page　分页参数
     * @param search　搜索参数
     * @return IPage
     */
    IPage<SysUser> listPage(Page page, Search search);

    List<SysUserPOI> export();

}
