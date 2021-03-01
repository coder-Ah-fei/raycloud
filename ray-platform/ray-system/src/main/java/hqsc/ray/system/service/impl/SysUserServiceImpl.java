package hqsc.ray.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hqsc.ray.system.entity.SysUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.core.web.util.CollectionUtil;
import hqsc.ray.system.mapper.SysUserMapper;
import hqsc.ray.system.poi.SysUserPOI;
import hqsc.ray.system.service.ISysDepartService;
import hqsc.ray.system.service.ISysDictService;
import hqsc.ray.system.service.ISysRoleService;
import hqsc.ray.system.service.ISysUserService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xuzf
 * @since 2020-06-18
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final ISysDepartService sysDepartService;
    private final ISysDictService dictService;
    private final ISysRoleService sysRoleService;

    @Override
    public boolean status(String ids, String status) {
        Collection<? extends Serializable> collection = CollectionUtil.stringToCollection(ids);

        for (Object id: collection) {
            SysUser sysUser = this.baseMapper.selectById(CollectionUtil.objectToLong(id, 0L));
            sysUser.setStatus(status);
            this.baseMapper.updateById(sysUser);
        }
        return true;
    }

    @Override
    public IPage<SysUser> listPage(Page page, Search search) {

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtil.isNotBlank(search.getStartDate())) {
            queryWrapper.between(SysUser::getCreateTime, search.getStartDate(), search.getEndDate());
        }
        if (StringUtil.isNotBlank(search.getKeyword())) {
            queryWrapper.like(SysUser::getName, search.getKeyword());
            queryWrapper.or();
            queryWrapper.like(SysUser::getId, search.getKeyword());
        }
        // 根据排序字段进行排序
        if (StringUtil.isNotBlank(search.getProp())) {
            if ("asc".equalsIgnoreCase(search.getOrder())) {
                queryWrapper.orderByAsc(SysUser::getId);
            } else {
                queryWrapper.orderByDesc(SysUser::getId);
            }
        }
        IPage<SysUser> sysUserIPage = this.baseMapper.selectPage(page, queryWrapper);
        List<SysUser> sysUserList = sysUserIPage.getRecords().stream().map(sysUser -> {
            sysUser.setDepartName(sysDepartService.getById(sysUser.getDepartId()).getName());
            sysUser.setStatusName(dictService.getValue("status", sysUser.getStatus()).getData());
            sysUser.setRoleName(sysRoleService.getById(sysUser.getRoleId()).getRoleName());
            return sysUser;
        }).collect(Collectors.toList());
        sysUserIPage.setRecords(sysUserList);
        return sysUserIPage;
    }

    @Override
    public List<SysUserPOI> export() {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getIsDeleted, "0");
        List<SysUser> sysUsers = this.baseMapper.selectList(queryWrapper);
        return sysUsers.stream().map(sysUser -> {
           SysUserPOI sysUserPOI = new SysUserPOI();
           BeanUtils.copyProperties(sysUser, sysUserPOI);
           sysUserPOI.setDepartName(sysDepartService.getById(sysUser.getDepartId()).getName());
           sysUserPOI.setRoleName(sysRoleService.getById(sysUser.getRoleId()).getRoleName());
           sysUserPOI.setStatusName(dictService.getValue("status", sysUser.getStatus()).getData());
           return sysUserPOI;
        }).collect(Collectors.toList());
    }
}
