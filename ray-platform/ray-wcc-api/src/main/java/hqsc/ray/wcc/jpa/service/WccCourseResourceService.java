package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccCourseResourceForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccCourseResourceService {

	/**
	 * 获取数据
	 *
	 * @param wccCourseResourceForm
	 * @return ResultMap
	 */
	ResultMap listWccCourseResources(WccCourseResourceForm wccCourseResourceForm);

}
