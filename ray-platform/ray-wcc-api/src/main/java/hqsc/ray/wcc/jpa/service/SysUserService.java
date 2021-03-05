package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysUserForm;

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
