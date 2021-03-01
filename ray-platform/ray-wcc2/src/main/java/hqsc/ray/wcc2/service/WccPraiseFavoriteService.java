package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccPraiseFavoriteForm;

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

}
