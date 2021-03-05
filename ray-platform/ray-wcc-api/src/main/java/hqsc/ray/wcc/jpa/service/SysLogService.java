package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysLogForm;

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
