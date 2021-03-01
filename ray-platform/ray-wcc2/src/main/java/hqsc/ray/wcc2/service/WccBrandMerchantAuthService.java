package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccBrandMerchantAuthForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccBrandMerchantAuthService {

	/**
	 * 获取数据
	 *
	 * @param wccBrandMerchantAuthForm
	 * @return ResultMap
	 */
	ResultMap listWccBrandMerchantAuths(WccBrandMerchantAuthForm wccBrandMerchantAuthForm);

}
