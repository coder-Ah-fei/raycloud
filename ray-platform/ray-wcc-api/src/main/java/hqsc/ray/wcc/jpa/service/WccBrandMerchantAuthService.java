package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccBrandMerchantAuthForm;

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
