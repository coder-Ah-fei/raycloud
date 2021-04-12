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
	
	
	/**
	 * 查询点赞或者是收藏数量
	 *
	 * @param type
	 * @param praiseFavoriteType
	 * @param belongId
	 * @param status
	 * @param isDelete
	 * @return
	 */
	Integer countByTypeAndPraiseFavoriteTypeAndAndBelongIdAndStatusAndIsDelete(Integer type, Integer praiseFavoriteType, Long belongId, Integer status, Integer isDelete);
	
	/**
	 * 查询点赞或者是收藏数量
	 *
	 * @param userId
	 * @param type
	 * @param praiseFavoriteType
	 * @param belongId
	 * @param status
	 * @param isDelete
	 * @return
	 */
	Integer countByJpaWccUserIdAndTypeAndPraiseFavoriteTypeAndAndBelongIdAndStatusAndIsDelete(Long userId, Integer type, Integer praiseFavoriteType, Long belongId, Integer status, Integer isDelete);
	
}
