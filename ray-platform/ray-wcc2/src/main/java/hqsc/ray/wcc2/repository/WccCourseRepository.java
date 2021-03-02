package hqsc.ray.wcc2.repository;

import hqsc.ray.wcc2.entity.WccCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccCourseRepository extends JpaRepository<WccCourse, Long>, JpaSpecificationExecutor {
	
	
	/**
	 * 获取收藏课程
	 *
	 * @param userId
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT * FROM wcc_course wc, wcc_praise_favorite wpf WHERE 1 = 1 AND wc.COURSE_ID = wpf.BELONG_ID AND wpf.TYPE = 5 AND wpf.USER_ID = ?1",
			countQuery = "SELECT count(*) FROM wcc_course wc, wcc_praise_favorite wpf WHERE 1 = 1 AND wc.COURSE_ID = wpf.BELONG_ID AND wpf.TYPE = 5 AND wpf.USER_ID = ?1\n",
			nativeQuery = true)
	Page<WccCourse> listWccCoursesFavorites(Long userId, Pageable pageable);
	
}
