package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccMerchantBrandDetailDto;
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
	
	/**
	 * 品牌方/商家详情表信息
	 *
	 * @param wccMerchantBrandDetailForm
	 * @return
	 */
	WccMerchantBrandDetailDto findById(WccMerchantBrandDetailForm wccMerchantBrandDetailForm);
	
	/**
	 * 品牌方/商家详情表设置,支持新增或修改
	 *
	 * @param wccMerchantBrandDetailForm
	 * @return
	 */
	Result<?> save(WccMerchantBrandDetailForm wccMerchantBrandDetailForm);
}
