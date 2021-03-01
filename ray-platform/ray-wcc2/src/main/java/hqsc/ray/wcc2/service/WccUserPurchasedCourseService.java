package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccUserPurchasedCourseForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserPurchasedCourseService {

	/**
	 * 获取数据
	 *
	 * @param wccUserPurchasedCourseForm
	 * @return ResultMap
	 */
	ResultMap listWccUserPurchasedCourses(WccUserPurchasedCourseForm wccUserPurchasedCourseForm);

}
