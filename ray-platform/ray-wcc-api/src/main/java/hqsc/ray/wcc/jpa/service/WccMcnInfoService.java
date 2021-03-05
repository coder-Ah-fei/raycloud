package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
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
	
}
