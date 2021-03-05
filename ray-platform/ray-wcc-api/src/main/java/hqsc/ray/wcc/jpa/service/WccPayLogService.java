package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccPayLogForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccPayLogService {

	/**
	 * 获取数据
	 *
	 * @param wccPayLogForm
	 * @return ResultMap
	 */
	ResultMap listWccPayLogs(WccPayLogForm wccPayLogForm);

}
