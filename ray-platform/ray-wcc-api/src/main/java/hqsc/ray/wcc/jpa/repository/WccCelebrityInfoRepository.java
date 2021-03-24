package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaWccCelebrityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccCelebrityInfoRepository extends JpaRepository<JpaWccCelebrityInfo, Long>, JpaSpecificationExecutor {
	
	
	/**
	 * 根据平台分组查询
	 *
	 * @return
	 */
	@Query(value = "FROM JpaWccCelebrityInfo o group by o.platform")
	List<JpaWccCelebrityInfo> findByGroupByPlatform();
	
	/**
	 * 根据领域分组查询
	 *
	 * @return
	 */
	@Query(value = "FROM JpaWccCelebrityInfo o group by o.scope")
	List<JpaWccCelebrityInfo> findByGroupByScope();
	
}
