package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccBannerDto;
import hqsc.ray.wcc.jpa.form.WccBannerForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccBannerService {
	
	/**
	 * 获取数据
	 *
	 * @param wccBannerForm
	 * @return ResultMap
	 */
	ResultMap listWccBanners(WccBannerForm wccBannerForm);
	
	/**
	 * 获取banner
	 *
	 * @param id
	 * @return
	 */
	WccBannerDto findById(Long id);
	
	/**
	 * 保存banner
	 *
	 * @param wccBannerForm
	 * @return
	 */
	Result<?> save(WccBannerForm wccBannerForm);
}
