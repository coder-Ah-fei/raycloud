package hqsc.ray.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.system.entity.SysLog;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author pangu
 * @since 2020-07-15
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 日志分页列表
     * @param page
     * @param search
     * @return
     */
    IPage<SysLog> listPage(Page page, Search search);
}
