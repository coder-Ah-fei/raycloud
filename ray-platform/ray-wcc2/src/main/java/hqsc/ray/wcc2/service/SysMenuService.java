package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysMenuForm;

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
