package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaSysArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysAreaRepository extends JpaRepository<JpaSysArea, Long>, JpaSpecificationExecutor {
	
	List<JpaSysArea> findByAdcode(String adcode);
	
	List<JpaSysArea> findByLevelOrderById(Integer level);
	
}
