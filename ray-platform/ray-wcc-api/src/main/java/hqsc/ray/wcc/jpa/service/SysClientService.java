package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysClientForm;

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
