package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.SysDepartForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface SysDepartService {

	/**
	 * 获取数据
	 *
	 * @param sysDepartForm
	 * @return ResultMap
	 */
	ResultMap listSysDeparts(SysDepartForm sysDepartForm);

}
