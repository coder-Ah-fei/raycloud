package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccPraiseFavoriteForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccPraiseFavoriteService {
	
	/**
	 * 获取数据
	 *
	 * @param wccPraiseFavoriteForm
	 * @return ResultMap
	 */
	ResultMap listWccPraiseFavorites(WccPraiseFavoriteForm wccPraiseFavoriteForm);
	
	/**
	 * 用户点赞/取消点赞
	 *
	 * @param wccPraiseFavoriteForm
	 * @return
	 */
	Result<?> likeOrUnlike(WccPraiseFavoriteForm wccPraiseFavoriteForm);
	
	/**
	 * 查询点赞或者是收藏数量
	 *
	 * @param type               0点赞还是1收藏
	 * @param praiseFavoriteType 被点赞/收藏的内容的类型(0回复1提问2文章3话题4视频5课程(章节))
	 * @param belongId           被点赞/收藏的内容的id
	 * @return
	 */
	Integer countByTypeAndPraiseFavoriteTypeAndAndBelongId(Integer type, Integer praiseFavoriteType, Long belongId);
	
	/**
	 * 查询点赞或者是收藏数量
	 * 当前登录用户是否已经点赞/收藏
	 *
	 * @param userId             点赞/收藏的用户的id
	 * @param type               0点赞还是1收藏
	 * @param praiseFavoriteType 被点赞/收藏的内容的类型(0回复1提问2文章3话题4视频5课程(章节))
	 * @param belongId           被点赞/收藏的内容的id
	 * @return
	 */
	Integer countByJpaWccUserIdAndTypeAndPraiseFavoriteTypeAndAndBelongId(Long userId, Integer type, Integer praiseFavoriteType, Long belongId);
	
}
