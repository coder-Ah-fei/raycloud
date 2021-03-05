package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccUserCircleForm;

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
