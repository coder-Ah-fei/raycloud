package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccMcnInfoDto;
import hqsc.ray.wcc.jpa.form.WccMcnInfoForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccMcnInfoService {
	
	/**
	 * 获取数据
	 *
	 * @param wccMcnInfoForm
	 * @return ResultMap
	 */
	ResultMap listWccMcnInfos(WccMcnInfoForm wccMcnInfoForm);
	
	/**
	 * mcn机构信息
	 *
	 * @param id
	 * @return
	 */
	WccMcnInfoDto findById(Long id);
	
	/**
	 * mcn机构信息设置
	 *
	 * @param wccMcnInfoForm
	 * @return
	 */
	Result<?> save(WccMcnInfoForm wccMcnInfoForm);
}
