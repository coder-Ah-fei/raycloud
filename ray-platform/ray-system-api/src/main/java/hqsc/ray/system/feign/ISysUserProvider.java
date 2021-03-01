package hqsc.ray.system.feign;

import hqsc.ray.system.entity.SysUser;
import hqsc.ray.wcc.entity.WccUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.constant.ProviderConstant;
import hqsc.ray.core.feign.constant.FeignConstant;
import hqsc.ray.system.dto.UserInfo;

/**
 * 用户调用类
 *
 * @author pangu
 */
@FeignClient(value = FeignConstant.RAY_CLOUD_SYSTEM)
public interface ISysUserProvider {

    /**
     * 根据id查询用户信息
     *
     * @param id id
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_USER_ID)
    Result<SysUser> getUserById(@RequestParam("id") Long id);

    /**
     * 根据userName查询用户信息
     * @param userName　用户名
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_USER_USERNAME)
    Result<UserInfo> getUserByUserName(@RequestParam("userName") String userName);

    /**
     * 根据手机号查询用户信息
     * @param mobile　手机号码
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_USER_MOBILE)
    Result<UserInfo> getUserByMobile(@RequestParam("mobile") String mobile);

}
