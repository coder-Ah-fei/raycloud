package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccOpenVipPriceForm;

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
