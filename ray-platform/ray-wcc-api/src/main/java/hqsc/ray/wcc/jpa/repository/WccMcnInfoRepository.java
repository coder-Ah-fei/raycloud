package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaWccMcnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccMcnInfoRepository extends JpaRepository<JpaWccMcnInfo, Long>, JpaSpecificationExecutor {
	
	/**
	 * 根据address 进行分组
	 *
	 * @return
	 */
	@Query(value = "FROM JpaWccMcnInfo o GROUP BY o.address")
	List<JpaWccMcnInfo> findByGroupByAddress();
	
	/**
	 * 根据领域进行分组查询
	 *
	 * @return
	 */
	@Query(value = "FROM JpaWccMcnInfo o GROUP BY o.field")
	List<JpaWccMcnInfo> findByGroupByFieldd();
	
}
