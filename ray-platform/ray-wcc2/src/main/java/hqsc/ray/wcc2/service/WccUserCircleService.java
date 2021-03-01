package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccUserCircleForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserCircleService {

	/**
	 * 获取数据
	 *
	 * @param wccUserCircleForm
	 * @return ResultMap
	 */
	ResultMap listWccUserCircles(WccUserCircleForm wccUserCircleForm);

}
