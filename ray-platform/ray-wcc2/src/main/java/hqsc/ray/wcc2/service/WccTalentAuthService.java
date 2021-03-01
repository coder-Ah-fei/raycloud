package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccTalentAuthForm;

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
