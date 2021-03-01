package hqsc.ray.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hqsc.ray.system.entity.SysRolePermission;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hqsc.ray.system.entity.SysRole;
import hqsc.ray.system.mapper.SysRoleMapper;
import hqsc.ray.system.poi.SysRolePOI;
import hqsc.ray.system.service.ISysRolePermissionService;
import hqsc.ray.system.service.ISysRoleService;
import hqsc.ray.system.vo.SysRoleVO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

    @Override
    public List<SysRoleVO> tree() {
        return this.baseMapper.tree();
    }

    @Override
    public List<SysRole> listSearch(Map<String, String> search) {
        String keyword = String.valueOf(search.get("keyword"));
        String startDate = String.valueOf(search.get("startDate"));
        String endDate = String.valueOf(search.get("endDate"));
        LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(startDate) && !startDate.equals("null")) {
            lambdaQueryWrapper.between(SysRole::getCreateTime, startDate, endDate);
        }
        if (StringUtils.isNotBlank(keyword) && !keyword.equals("null")) {
            lambdaQueryWrapper.like(SysRole::getRoleName, keyword);
            lambdaQueryWrapper.or();
            lambdaQueryWrapper.like(SysRole::getId, keyword);
        }
        return this.baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<String> getPermission(String id) {
        LambdaQueryWrapper<SysRolePermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRolePermission::getRoleId, id);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionService.list(lambdaQueryWrapper);
        List<String> list = sysRolePermissions.stream().map(sysRolePermission -> {
            String menuId = sysRolePermission.getMenuId().toString();
            return menuId;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<SysRolePOI> export() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getIsDeleted, 0);
        List<SysRole> sysRoles = this.baseMapper.selectList(queryWrapper);
        return sysRoles.stream().map(sysRole -> {
            SysRolePOI sysRolePOI = new SysRolePOI();
            BeanUtils.copyProperties(sysRole, sysRolePOI);
            return sysRolePOI;
        }).collect(Collectors.toList());
    }
}
