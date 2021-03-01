package hqsc.ray.component.feign;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import hqsc.ray.component.config.AttachmentConfig;
import hqsc.ray.component.entity.SysAttachment;
import hqsc.ray.component.service.ISysAttachmentService;
import hqsc.ray.component.util.UploadUtils;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.constant.Oauth2Constant;
import hqsc.ray.core.common.constant.ProviderConstant;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.log.util.TrackUtil;
import hqsc.ray.core.web.util.OssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 远程调用获取附件信息
 * @author xuzhanfu
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "附件远程调用")
public class SysAttachmentProvider implements ISysAttachmentProvider {

    private final ISysAttachmentService sysAttachmentService;
    @Autowired
    AttachmentConfig attachmentConfig;
    @Override
    @GetMapping(ProviderConstant.PROVIDER_ATT_ADD)
    @Log(value = "头像url保存", exception = "头像url保存请求失败")
    @ApiOperation(value = "头像url保存", notes = "头像url保存")
    public SysAttachment crateAtt(String url) {
        UploadUtils util = new UploadUtils();
        util.setSavePath(attachmentConfig.getUploadPath()+"upload/");
        util.setSaveUrl("/" + util.getBasePath() + "/");
        util.setBasePath(util.getBasePath());
        String[] infos = util.saveFileFromUrl(url);
        String headimgurl = infos[1];
        String fileName = infos[2];
        SysAttachment att = new SysAttachment();
        att.setName(fileName);
        att.setUrl(headimgurl);
        att.setPath(headimgurl);
        att.setFileExtend("jpg");
        att.setType(1);
        att.setCreateTime(LocalDateTime.now());
        att.setUpdateTime(LocalDateTime.now());
        sysAttachmentService.save(att);
        return att;
    }

    @Override
    public String getAttUrlById(Long id) {
        return sysAttachmentService.getById(id).getUrl();
    }

    @Override
    public List<String> getAttUrlsByIds(List<Long> ids) {
        List<SysAttachment> sysAttachments = sysAttachmentService.listByIds(ids);
        ArrayList<String> list = new ArrayList<>();
        for (SysAttachment sysAttachment : sysAttachments) {
            list.add(sysAttachment.getUrl());
        }
        return list;
    }


}
