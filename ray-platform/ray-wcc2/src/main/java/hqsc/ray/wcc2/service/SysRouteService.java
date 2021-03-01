package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysRouteForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysRouteService {

	/**
	 * 获取数据
	 *
	 * @param sysRouteForm
	 * @return ResultMap
	 */
	ResultMap listSysRoutes(SysRouteForm sysRouteForm);

}
