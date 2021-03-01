package hqsc.ray.system.feign;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import hqsc.ray.core.common.constant.Oauth2Constant;
import hqsc.ray.system.dto.UserInfo;
import hqsc.ray.system.entity.SysUser;
import hqsc.ray.system.service.ISysRolePermissionService;
import hqsc.ray.system.service.ISysUserService;
import hqsc.ray.wcc.entity.WccUser;
import hqsc.ray.wcc.service.IWccUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.constant.ProviderConstant;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.log.util.TrackUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 远程调用获取用户信息
 * @author xuzhanfu
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "用户远程调用")
public class SysUserProvider implements ISysUserProvider {

    private final ISysUserService sysUserService;
    private final ISysRolePermissionService sysRolePermissionService;
    private final IWccUserService wccUserService;

    @Override
    @GetMapping(ProviderConstant.PROVIDER_USER_ID)
    @Log(value = "用户ID查询", exception = "用户ID查询请求失败")
    @ApiOperation(value = "用户ID查询", notes = "用户ID查询")
    public Result<SysUser> getUserById(Long id) {
        SysUser sysUser = sysUserService.getById(id);
        // 测试日志埋点
        TrackUtil.info(SysUserProvider.class.getName(), "userInfoById", JSON.toJSONString(sysUser));
        return Result.data(sysUser);
    }


    @Override
    @GetMapping(ProviderConstant.PROVIDER_USER_USERNAME)
    @Log(value = "用户名查询用户", exception = "用户名查询用户请求失败")
    @ApiOperation(value = "用户用户名查询", notes = "用户用户名查询")
    public Result<UserInfo> getUserByUserName(String userName) {
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getAccount, userName));
        return Result.data(getUserInfo(sysUser));
    }

    @Override
    @GetMapping(ProviderConstant.PROVIDER_USER_MOBILE)
    @Log(value = "用户手机号查询", exception = "用户手机号查询请求失败")
    @ApiOperation(value = "用户手机号查询", notes = "用户手机号查询")
    public Result<UserInfo> getUserByMobile(String mobile) {
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getTelephone, mobile));
        return Result.data(getUserInfo(sysUser));
    }

    public UserInfo getUserInfo(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        userInfo.setUserType(Oauth2Constant.USER_TYPE_MANAGER);
        userInfo.setPermissions(sysRolePermissionService.getMenuIdByRoleId(sysUser.getRoleId().toString()));
        List<Long> longs = new ArrayList<>();
        longs.add(sysUser.getRoleId());
        userInfo.setRoleIds(longs);
        userInfo.setTenantId(sysUser.getTenantId());
        log.debug("feign调用：userInfo:{}", userInfo);
        return userInfo;
    }

    public UserInfo getWUserInfo(WccUser wccUser) {
        if (wccUser == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setWccUser(wccUser);
        userInfo.setUserType(Oauth2Constant.USER_TYPE_USER);
        log.debug("feign调用：userInfo:{}", userInfo);
        return userInfo;
    }



}
