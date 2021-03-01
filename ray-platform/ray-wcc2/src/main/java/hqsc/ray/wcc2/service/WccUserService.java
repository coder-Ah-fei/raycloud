package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccUserForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserService {

	/**
	 * 获取数据
	 *
	 * @param wccUserForm
	 * @return ResultMap
	 */
	ResultMap listWccUsers(WccUserForm wccUserForm);

}
