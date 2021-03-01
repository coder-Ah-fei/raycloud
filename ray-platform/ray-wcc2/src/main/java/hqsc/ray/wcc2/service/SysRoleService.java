package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysRoleForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysRoleService {

	/**
	 * 获取数据
	 *
	 * @param sysRoleForm
	 * @return ResultMap
	 */
	ResultMap listSysRoles(SysRoleForm sysRoleForm);

}
