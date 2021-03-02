package hqsc.ray.wcc2.repository;

import hqsc.ray.wcc2.entity.RaySysAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface RaySysAttachmentRepository extends JpaRepository<RaySysAttachment, Long>, JpaSpecificationExecutor {

}
