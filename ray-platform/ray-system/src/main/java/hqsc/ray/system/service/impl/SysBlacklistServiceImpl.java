package hqsc.ray.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hqsc.ray.system.mapper.SysBlacklistMapper;
import hqsc.ray.system.service.ISysBlacklistService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.core.rule.entity.BlackList;
import hqsc.ray.core.rule.service.IRuleCacheService;
import hqsc.ray.core.web.util.CollectionUtil;
import hqsc.ray.system.entity.SysBlacklist;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 系统黑名单表 服务实现类
 * </p>
 *
 * @author pangu
 * @since 2020-08-26
 */
@Service
@AllArgsConstructor
public class SysBlacklistServiceImpl extends ServiceImpl<SysBlacklistMapper, SysBlacklist> implements ISysBlacklistService {

    private final IRuleCacheService ruleCacheService;

    @Override
    public Page listPage(Page page, Search search) {
        LambdaQueryWrapper<SysBlacklist> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtil.isNotBlank(search.getStartDate())) {
            lambdaQueryWrapper.between(SysBlacklist::getCreateTime, search.getStartDate(), search.getEndDate());
        }
        String keyword = search.getKeyword();
        if (StringUtils.isNotBlank(keyword) && !keyword.equals("null")) {
            lambdaQueryWrapper.like(SysBlacklist::getId, keyword);
            lambdaQueryWrapper.or();
            lambdaQueryWrapper.like(SysBlacklist::getRequestUri, keyword);
        }
        return this.baseMapper.selectPage(page, lambdaQueryWrapper);
    }

    @Override
    public boolean status(String ids, String status) {
        Collection<? extends Serializable> collection = CollectionUtil.stringToCollection(ids);

        for (Object id: collection) {
            SysBlacklist sysBlacklist = this.baseMapper.selectById(CollectionUtil.objectToLong(id, 0L));
            sysBlacklist.setStatus(status);
            this.baseMapper.updateById(sysBlacklist);
            //缓存操作-----start
            BlackList blackList = new BlackList();
            BeanUtils.copyProperties(sysBlacklist, blackList);
            ruleCacheService.setBlackList(blackList);
            //缓存操作----end
        }
        return true;
    }
}
