package hqsc.ray.wcc2.repository;

import hqsc.ray.wcc2.entity.WccCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccCourseRepository extends JpaRepository<WccCourse, Integer>, JpaSpecificationExecutor {

}
