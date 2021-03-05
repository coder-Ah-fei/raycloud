package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccMerchantBrandDetailForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccMerchantBrandDetailService {

	/**
	 * 获取数据
	 *
	 * @param wccMerchantBrandDetailForm
	 * @return ResultMap
	 */
	ResultMap listWccMerchantBrandDetails(WccMerchantBrandDetailForm wccMerchantBrandDetailForm);

}
