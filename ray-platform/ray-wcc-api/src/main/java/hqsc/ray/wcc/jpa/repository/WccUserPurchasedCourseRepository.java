package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaWccUserPurchasedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserPurchasedCourseRepository extends JpaRepository<JpaWccUserPurchasedCourse, Long>, JpaSpecificationExecutor {
	
	/**
	 * 查询用户是否购买某课程
	 *
	 * @param userId
	 * @param courseId
	 * @param status
	 * @param isDelete
	 * @return
	 */
	Integer countByJpaWccUserIdAndJpaWccCourseIdAndStatusAndIsDelete(Long userId, Long courseId, Integer status, Integer isDelete);
	
}
