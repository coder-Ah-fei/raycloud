package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccBannerDto;
import hqsc.ray.wcc.jpa.form.SysAreaForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysAreaService {
	
	/**
	 * 获取数据
	 *
	 * @param sysAreaForm
	 * @return ResultMap
	 */
	PageMap<WccBannerDto> listSysAreas(SysAreaForm sysAreaForm);
	
	Result<?> save();
	
	/**
	 * sysAreaService.list()
	 *
	 * @return
	 */
	Result<?> list();
}
