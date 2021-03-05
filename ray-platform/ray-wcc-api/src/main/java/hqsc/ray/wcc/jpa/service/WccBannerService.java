package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
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

}
