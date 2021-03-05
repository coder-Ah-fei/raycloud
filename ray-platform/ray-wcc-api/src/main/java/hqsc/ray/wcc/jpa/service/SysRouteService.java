package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysRouteForm;

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
