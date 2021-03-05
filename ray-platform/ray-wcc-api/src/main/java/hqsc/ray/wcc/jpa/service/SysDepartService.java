package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.SysDepartForm;

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
