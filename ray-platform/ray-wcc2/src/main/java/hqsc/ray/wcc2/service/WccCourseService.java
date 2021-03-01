package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccCourseForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccCourseService {

	/**
	 * 获取数据
	 *
	 * @param wccCourseForm
	 * @return ResultMap
	 */
	ResultMap listWccCourses(WccCourseForm wccCourseForm);

}
