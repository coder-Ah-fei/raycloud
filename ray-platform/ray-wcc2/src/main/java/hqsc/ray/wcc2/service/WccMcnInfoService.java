package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccMcnInfoForm;

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
	
}
