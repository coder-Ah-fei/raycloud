package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccCircleInfoForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccCircleInfoService {

	/**
	 * 获取数据
	 *
	 * @param wccCircleInfoForm
	 * @return ResultMap
	 */
	ResultMap listWccCircleInfos(WccCircleInfoForm wccCircleInfoForm);

}
