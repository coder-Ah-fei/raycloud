package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccTalentAuthForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccTalentAuthService {

	/**
	 * 获取数据
	 *
	 * @param wccTalentAuthForm
	 * @return ResultMap
	 */
	ResultMap listWccTalentAuths(WccTalentAuthForm wccTalentAuthForm);

}
