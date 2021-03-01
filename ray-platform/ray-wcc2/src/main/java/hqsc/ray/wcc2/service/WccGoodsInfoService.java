package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccGoodsInfoForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccGoodsInfoService {

	/**
	 * 获取数据
	 *
	 * @param wccGoodsInfoForm
	 * @return ResultMap
	 */
	ResultMap listWccGoodsInfos(WccGoodsInfoForm wccGoodsInfoForm);

}
