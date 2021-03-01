package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccPayLogForm;

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
