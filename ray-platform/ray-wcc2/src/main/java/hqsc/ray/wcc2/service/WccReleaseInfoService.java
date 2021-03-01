package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccReleaseInfoForm;

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
