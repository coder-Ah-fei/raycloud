package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccTaskDetailForm;

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
