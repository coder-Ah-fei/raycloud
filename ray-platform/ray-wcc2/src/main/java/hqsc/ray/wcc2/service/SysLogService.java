package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysLogForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysLogService {

	/**
	 * 获取数据
	 *
	 * @param sysLogForm
	 * @return ResultMap
	 */
	ResultMap listSysLogs(SysLogForm sysLogForm);

}
