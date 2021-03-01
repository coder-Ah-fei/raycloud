package hqsc.ray.core.log.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.constant.ProviderConstant;
import hqsc.ray.core.common.dto.CommonLog;
import hqsc.ray.core.feign.constant.FeignConstant;

/**
 * feign调用ray-system存储日志
 * @author pangu
 * @date 2020-7-1
 */
@FeignClient(value = FeignConstant.RAY_CLOUD_SYSTEM)
public interface ISysLogProvider {

    /**
     * 日志设置
     * @param commonLog　CommonLog对象
     * @return Result
     */
    @PostMapping(ProviderConstant.PROVIDER_LOG_SET)
    Result<Boolean> set(@RequestBody CommonLog commonLog);

}
