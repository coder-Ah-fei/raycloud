package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCourseDto;
import hqsc.ray.wcc.jpa.form.WccCourseForm;

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
	PageMap<WccCourseDto> listWccCourses(WccCourseForm wccCourseForm);
	
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
	
	/**
	 * 根据id获取课程
	 *
	 * @param wccCourseForm
	 * @return
	 */
	WccCourseDto findById(WccCourseForm wccCourseForm);
	
	/**
	 * 保存课程,支持新增或修改
	 *
	 * @param wccCourseForm
	 * @return
	 */
	Result<?> save(WccCourseForm wccCourseForm);
}
