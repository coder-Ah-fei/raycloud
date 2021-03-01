package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccOrganizationDetailForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccOrganizationDetailService {

	/**
	 * 获取数据
	 *
	 * @param wccOrganizationDetailForm
	 * @return ResultMap
	 */
	ResultMap listWccOrganizationDetails(WccOrganizationDetailForm wccOrganizationDetailForm);

}
