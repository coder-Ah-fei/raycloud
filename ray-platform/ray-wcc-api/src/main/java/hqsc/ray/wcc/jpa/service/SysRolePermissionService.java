package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysRolePermissionForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysRolePermissionService {

	/**
	 * 获取数据
	 *
	 * @param sysRolePermissionForm
	 * @return ResultMap
	 */
	ResultMap listSysRolePermissions(SysRolePermissionForm sysRolePermissionForm);

}
