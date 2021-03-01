package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysRolePermissionForm;

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
