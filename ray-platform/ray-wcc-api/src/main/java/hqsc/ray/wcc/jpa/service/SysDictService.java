package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysDictForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysDictService {

	/**
	 * 获取数据
	 *
	 * @param sysDictForm
	 * @return ResultMap
	 */
	ResultMap listSysDicts(SysDictForm sysDictForm);

}
