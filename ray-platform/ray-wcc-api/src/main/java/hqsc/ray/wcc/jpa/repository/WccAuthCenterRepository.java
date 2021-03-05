package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaWccAuthCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccAuthCenterRepository extends JpaRepository<JpaWccAuthCenter, Long>, JpaSpecificationExecutor {
	
	/**
	 * 根据用户查找
	 *
	 * @param userId
	 * @return
	 */
	List<JpaWccAuthCenter> findByJpaWccUserId(Long userId);
	
}
