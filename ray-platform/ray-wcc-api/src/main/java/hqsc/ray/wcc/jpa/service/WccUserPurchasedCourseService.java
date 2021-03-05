package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccUserPurchasedCourseForm;

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
