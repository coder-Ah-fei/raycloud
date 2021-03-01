package hqsc.ray.wcc.feign;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.constant.Oauth2Constant;
import hqsc.ray.core.common.constant.ProviderConstant;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.log.util.TrackUtil;
import hqsc.ray.wcc.dto.UserInfo;
import hqsc.ray.wcc.entity.WccUser;
import hqsc.ray.wcc.service.IWccUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class WccUserProvider implements IWccUserProvider {

    private final IWccUserService WccUserService;
    private final IWccUserService wccUserService;

    @Override
    @GetMapping(ProviderConstant.PROVIDER_USER_UNIONID)
    @Log(value = "用户微信id查询", exception = "用户微信id查询请求失败")
    @ApiOperation(value = "用户微信id查询", notes = "用户微信id查询")
    public Result<UserInfo> getWUserByUnionId(String unionId) {
        WccUser wccUser=wccUserService.getOne(new LambdaQueryWrapper<WccUser>()
                .eq(WccUser::getWechatUnionId, unionId));
        return Result.data(getWUserInfo(wccUser));
    }

    @Override
    @PostMapping(ProviderConstant.PROVIDER_USER_ADD)
    @Log(value = "创建前端用户", exception = "创建前端用户失败")
    @ApiOperation(value = "创建前端用户", notes = "创建前端用户")
    public Result<?> createWUser(WccUser wccUser) {
        boolean bo = wccUserService.save(wccUser);
        if (!bo){
            return Result.fail("创建失败");
        }
        return Result.success("创建成功");
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
