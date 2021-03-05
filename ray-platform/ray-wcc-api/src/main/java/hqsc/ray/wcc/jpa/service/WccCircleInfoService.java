package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccCircleInfoForm;

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
