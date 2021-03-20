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
}
