package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysUserForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysUserService {

	/**
	 * 获取数据
	 *
	 * @param sysUserForm
	 * @return ResultMap
	 */
	ResultMap listSysUsers(SysUserForm sysUserForm);

}
