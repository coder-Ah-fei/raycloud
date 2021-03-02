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
	
	/**
	 * 获取收藏的课程
	 *
	 * @param wccCourseForm
	 * @return
	 */
	ResultMap listWccCoursesFavorites(WccCourseForm wccCourseForm);
	
	/**
	 * 获取已购买的课程
	 *
	 * @param wccCourseForm
	 * @return
	 */
	ResultMap listWccCoursesBought(WccCourseForm wccCourseForm);
	
	/**
	 * 获取课程详细信息
	 *
	 * @param wccCourseForm
	 * @return
	 */
	ResultMap wccCourseDetail(WccCourseForm wccCourseForm);
}
