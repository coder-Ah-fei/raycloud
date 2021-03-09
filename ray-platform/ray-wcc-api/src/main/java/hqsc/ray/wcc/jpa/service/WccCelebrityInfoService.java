package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCelebrityInfoDto;
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
	
	/**
	 * 红人信息表信息
	 *
	 * @param id
	 * @return
	 */
	WccCelebrityInfoDto findById(Long id);
	
	/**
	 * 红人信息表设置
	 *
	 * @param wccCelebrityInfoForm
	 * @return
	 */
	Result<?> save(WccCelebrityInfoForm wccCelebrityInfoForm);
}
