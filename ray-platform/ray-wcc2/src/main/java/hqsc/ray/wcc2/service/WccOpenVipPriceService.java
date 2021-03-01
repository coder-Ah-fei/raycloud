package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccOpenVipPriceForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccOpenVipPriceService {

	/**
	 * 获取数据
	 *
	 * @param wccOpenVipPriceForm
	 * @return ResultMap
	 */
	ResultMap listWccOpenVipPrices(WccOpenVipPriceForm wccOpenVipPriceForm);

}
