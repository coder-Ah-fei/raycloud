package hqsc.ray.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hqsc.ray.system.entity.SysLog;
import org.springframework.stereotype.Service;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.system.mapper.SysLogMapper;
import hqsc.ray.system.service.ISysLogService;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author pangu
 * @since 2020-07-15
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Override
    public IPage<SysLog> listPage(Page page, Search search) {
        LambdaQueryWrapper<SysLog> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtil.isNotBlank(search.getStartDate())) {
            queryWrapper.between(SysLog::getCreateTime, search.getStartDate(), search.getEndDate());
        }
        if (StringUtil.isNotBlank(search.getKeyword())) {
            queryWrapper.like(SysLog::getTitle, search.getKeyword());
            queryWrapper.or();
            queryWrapper.like(SysLog::getTraceId, search.getKeyword());
        }
        if (StringUtil.isNotBlank(search.getProp())) {
            if ("asc".equalsIgnoreCase(search.getOrder())) {
                queryWrapper.orderByAsc(SysLog::getCreateTime);
            } else {
                queryWrapper.orderByDesc(SysLog::getCreateTime);
            }
        }
        queryWrapper.orderByDesc(SysLog::getCreateTime);
        return this.baseMapper.selectPage(page, queryWrapper);
    }
}
