package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaWccResponseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccResponseDetailsRepository extends JpaRepository<JpaWccResponseDetails, Long>, JpaSpecificationExecutor {
	
	
	/**
	 * 查找评论数
	 *
	 * @param releaseInfoId
	 * @param status
	 * @param isDelete
	 * @return
	 */
	Integer countByJpaWccReleaseInfoIdAndStatusAndIsDelete(Long releaseInfoId, Integer status, Integer isDelete);
	
}
