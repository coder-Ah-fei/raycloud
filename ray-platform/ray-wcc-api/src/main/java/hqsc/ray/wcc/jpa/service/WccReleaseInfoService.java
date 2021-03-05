package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccReleaseInfoForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccReleaseInfoService {

	/**
	 * 获取数据
	 *
	 * @param wccReleaseInfoForm
	 * @return ResultMap
	 */
	ResultMap listWccReleaseInfos(WccReleaseInfoForm wccReleaseInfoForm);

}
