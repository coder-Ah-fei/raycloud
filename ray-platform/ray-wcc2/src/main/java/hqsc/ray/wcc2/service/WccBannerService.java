package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccBannerForm;

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

}
