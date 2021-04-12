package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.UserAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface UserAttachmentRepository extends JpaRepository<UserAttachment, Long>, JpaSpecificationExecutor {
	
	/**
	 * 根据userId, attachmentId, canAttach 查找数据，以此判断权限
	 *
	 * @param userId
	 * @param attachmentId
	 * @param canAccess
	 * @return
	 */
	List<UserAttachment> findByJpaWccUserIdAndSysAttachmentIdAndCanAccess(Long userId, Long attachmentId, Boolean canAccess);
	
}
