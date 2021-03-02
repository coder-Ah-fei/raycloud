package hqsc.ray.wcc2.repository;

import hqsc.ray.wcc2.entity.WccAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccAttachmentRepository extends JpaRepository<WccAttachment, Long>, JpaSpecificationExecutor {

}
