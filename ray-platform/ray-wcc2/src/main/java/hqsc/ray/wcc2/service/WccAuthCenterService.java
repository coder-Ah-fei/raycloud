package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccAuthCenterForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccAuthCenterService {

	/**
	 * 获取数据
	 *
	 * @param wccAuthCenterForm
	 * @return ResultMap
	 */
	ResultMap listWccAuthCenters(WccAuthCenterForm wccAuthCenterForm);

}
