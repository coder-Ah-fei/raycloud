package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaWccUserConcern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserConcernRepository extends JpaRepository<JpaWccUserConcern, Long>, JpaSpecificationExecutor {
	
	
	Long countByJpaWccUserIdAndBelongUserId(Long userId, Long belongUserId);
	
	/**
	 * @param userId
	 * @param belongUserId
	 * @param status
	 * @param isdelete
	 * @return
	 */
	Long countByJpaWccUserIdAndBelongUserIdAndStatusAndIsDelete(Long userId, Long belongUserId, Integer status, Integer isdelete);
	
}
