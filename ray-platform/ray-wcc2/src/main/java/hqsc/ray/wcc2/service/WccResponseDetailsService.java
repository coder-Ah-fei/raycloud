package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccResponseDetailsForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccResponseDetailsService {
	
	/**
	 * 获取数据
	 *
	 * @param wccResponseDetailsForm
	 * @return ResultMap
	 */
	ResultMap listWccResponseDetailss(WccResponseDetailsForm wccResponseDetailsForm);
	
	/**
	 * 新增评论
	 *
	 * @param wccResponseDetailsForm
	 * @return
	 */
	ResultMap saveWccResponseDetails(WccResponseDetailsForm wccResponseDetailsForm);
}
