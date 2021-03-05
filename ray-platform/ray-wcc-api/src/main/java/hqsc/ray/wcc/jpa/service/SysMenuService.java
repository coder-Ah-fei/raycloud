package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysMenuForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysMenuService {

	/**
	 * 获取数据
	 *
	 * @param sysMenuForm
	 * @return ResultMap
	 */
	ResultMap listSysMenus(SysMenuForm sysMenuForm);

}
