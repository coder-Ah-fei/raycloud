package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysBlacklistForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysBlacklistService {

	/**
	 * 获取数据
	 *
	 * @param sysBlacklistForm
	 * @return ResultMap
	 */
	ResultMap listSysBlacklists(SysBlacklistForm sysBlacklistForm);

}
