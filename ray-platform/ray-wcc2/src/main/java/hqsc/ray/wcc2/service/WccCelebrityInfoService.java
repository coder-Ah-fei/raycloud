package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccCelebrityInfoForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccCelebrityInfoService {

	/**
	 * 获取数据
	 *
	 * @param wccCelebrityInfoForm
	 * @return ResultMap
	 */
	ResultMap listWccCelebrityInfos(WccCelebrityInfoForm wccCelebrityInfoForm);

}
