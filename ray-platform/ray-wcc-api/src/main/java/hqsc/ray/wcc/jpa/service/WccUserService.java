package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccUserForm;

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
