package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysApiForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysApiService {

	/**
	 * 获取数据
	 *
	 * @param sysApiForm
	 * @return ResultMap
	 */
	ResultMap listSysApis(SysApiForm sysApiForm);

}
