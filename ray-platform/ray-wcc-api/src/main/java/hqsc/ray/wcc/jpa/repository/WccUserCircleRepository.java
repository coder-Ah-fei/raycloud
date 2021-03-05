package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaWccUserCircle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserCircleRepository extends JpaRepository<JpaWccUserCircle, Long>, JpaSpecificationExecutor {
	
	/**
	 * 根据用户和圈子查找
	 *
	 * @param userId
	 * @param circleId
	 * @return
	 */
	List<JpaWccUserCircle> findByJpaWccUserIdAndJpaWccCircleInfoId(Long userId, Long circleId);
	
}
