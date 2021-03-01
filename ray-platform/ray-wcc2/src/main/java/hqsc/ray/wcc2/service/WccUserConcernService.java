package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccUserConcernForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserConcernService {

	/**
	 * 获取数据
	 *
	 * @param wccUserConcernForm
	 * @return ResultMap
	 */
	ResultMap listWccUserConcerns(WccUserConcernForm wccUserConcernForm);

}
