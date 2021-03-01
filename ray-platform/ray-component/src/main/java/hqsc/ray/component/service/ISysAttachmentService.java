package hqsc.ray.component.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import hqsc.ray.component.entity.SysAttachment;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.database.entity.Search;

/**
 * <p>
 * 附件表 服务类
 * </p>
 *
 * @author pangu
 * @since 2020-08-09
 */
public interface ISysAttachmentService extends IService<SysAttachment> {

    IPage<SysAttachment> listPage(Page page, Search search);

    Result<?> upload(MultipartFile file);

    boolean delete(Long id);

}
