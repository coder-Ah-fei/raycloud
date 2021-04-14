package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
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
	
	/**
	 * 绑定手机号
	 *
	 * @param encryptedData
	 * @param iv
	 * @param userId
	 * @return
	 */
	Result<?> bindMobilePhone(String encryptedData, String iv, String userId);
	
	/**
	 * 保存新增/修改用户
	 *
	 * @param userForm
	 * @return
	 */
	Result<?> save(WccUserForm userForm);
}
