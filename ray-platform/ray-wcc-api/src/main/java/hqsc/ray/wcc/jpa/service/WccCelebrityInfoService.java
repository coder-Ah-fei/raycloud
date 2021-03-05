package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccCelebrityInfoForm;

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
