package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.OrderCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface OrderCourseRepository extends JpaRepository<OrderCourse, Long>, JpaSpecificationExecutor {

}
