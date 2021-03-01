package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysDictForm;

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
