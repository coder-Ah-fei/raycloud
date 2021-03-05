package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysRoleForm;

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
