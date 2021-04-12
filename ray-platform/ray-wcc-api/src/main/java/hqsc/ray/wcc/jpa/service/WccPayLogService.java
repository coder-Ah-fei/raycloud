package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.PayLogForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccPayLogService {
	
	/**
	 * 获取数据
	 *
	 * @param payLogForm
	 * @return ResultMap
	 */
	ResultMap listWccPayLogs(PayLogForm payLogForm);
	
}
