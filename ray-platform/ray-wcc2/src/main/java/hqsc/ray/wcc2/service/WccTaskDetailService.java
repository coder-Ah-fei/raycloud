package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccTaskDetailForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccTaskDetailService {

	/**
	 * 获取数据
	 *
	 * @param wccTaskDetailForm
	 * @return ResultMap
	 */
	ResultMap listWccTaskDetails(WccTaskDetailForm wccTaskDetailForm);

}
