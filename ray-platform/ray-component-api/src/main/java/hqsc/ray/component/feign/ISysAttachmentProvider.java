package hqsc.ray.component.feign;

import hqsc.ray.component.entity.SysAttachment;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.constant.ProviderConstant;
import hqsc.ray.core.feign.constant.FeignConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 附件调用类
 *
 * @author pangu
 */
@FeignClient(value = FeignConstant.RAY_CLOUD_COMPONENT)
public interface ISysAttachmentProvider {

    /**
     * 根据url下载文件
     *
     * @param url url
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_ATT_ADD)
    SysAttachment crateAtt(@RequestParam("url") String url);

    @GetMapping(ProviderConstant.PROVIDER_ATT_URL)
    String getAttUrlById(@RequestParam("id") Long id);

    @GetMapping(ProviderConstant.PROVIDER_ATT_URLS)
    List<String> getAttUrlsByIds(@RequestParam("id") List<Long> ids);
}