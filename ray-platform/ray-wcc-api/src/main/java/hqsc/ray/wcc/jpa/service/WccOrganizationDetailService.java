package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccOrganizationDetailForm;

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
