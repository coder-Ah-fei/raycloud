package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccCircleInfoDto;
import hqsc.ray.wcc.jpa.form.WccCircleInfoForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccCircleInfoService {
	
	/**
	 * 获取数据
	 *
	 * @param wccCircleInfoForm
	 * @return ResultMap
	 */
	PageMap<WccCircleInfoDto> listWccCircleInfos(WccCircleInfoForm wccCircleInfoForm);
	
	/**
	 * 根据id获取圈子数据
	 *
	 * @param wccCircleInfoForm
	 * @return
	 */
	WccCircleInfoDto findById(WccCircleInfoForm wccCircleInfoForm);
	
	/**
	 * 保存圈子数据
	 *
	 * @param wccCircleInfoForm
	 * @return
	 */
	Result<?> save(WccCircleInfoForm wccCircleInfoForm);
}
