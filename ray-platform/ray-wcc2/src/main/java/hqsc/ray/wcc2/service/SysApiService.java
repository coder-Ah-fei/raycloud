package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysApiForm;

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
