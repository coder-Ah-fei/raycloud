package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccCourseResourceForm;

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
