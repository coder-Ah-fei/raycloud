package hqsc.ray.wcc.jpa.repository;

import hqsc.ray.wcc.jpa.entity.JpaWccPraiseFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccPraiseFavoriteRepository extends JpaRepository<JpaWccPraiseFavorite, Long>, JpaSpecificationExecutor {
	
	/**
	 * 根据参数查找数据
	 *
	 * @param userId
	 * @param type
	 * @param praiseFavoriteType
	 * @param belongId
	 * @return
	 */
	List<JpaWccPraiseFavorite> findByJpaWccUserIdAndTypeAndPraiseFavoriteTypeAndBelongId(Long userId, Integer type, Integer praiseFavoriteType, Long belongId);
	
	
}
