package hqsc.ray.wcc.feign;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.constant.ProviderConstant;
import hqsc.ray.core.feign.constant.FeignConstant;
import hqsc.ray.wcc.dto.UserInfo;
import hqsc.ray.wcc.entity.WccUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户调用类
 *
 * @author pangu
 */
@FeignClient(value = FeignConstant.RAY_CLOUD_WCC)
public interface IWccUserProvider {


    /**
     * 根据UnionId查询用户信息
     * @param unionId　微信ID
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_USER_UNIONID)
    Result<UserInfo> getWUserByUnionId(@RequestParam("unionId") String unionId);

    /**
     * 创建前端用户
     * @param wccUser 前端用户对象
     */
    @PostMapping(ProviderConstant.PROVIDER_USER_ADD)
    Result<?>  createWUser(@RequestBody WccUser wccUser);

}
