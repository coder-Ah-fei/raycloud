package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysClientForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysClientService {

	/**
	 * 获取数据
	 *
	 * @param sysClientForm
	 * @return ResultMap
	 */
	ResultMap listSysClients(SysClientForm sysClientForm);

}
