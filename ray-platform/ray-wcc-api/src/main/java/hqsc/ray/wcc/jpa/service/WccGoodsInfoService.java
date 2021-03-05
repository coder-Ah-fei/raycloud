package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccGoodsInfoForm;

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
