package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysBlacklistForm;

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
