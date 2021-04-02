package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCourseChapterDto;
import hqsc.ray.wcc.jpa.form.WccCourseChapterForm;
import hqsc.ray.wcc.jpa.form.WccCourseForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccCourseChapterService {
	
	/**
	 * 获取数据
	 *
	 * @param courseChapterForm
	 * @return ResultMap
	 */
	PageMap<WccCourseChapterDto> listWccCourseChapters(WccCourseChapterForm courseChapterForm);
	
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
	 * 保存课程,支持新增或修改
	 *
	 * @param courseChapterForm
	 * @return
	 */
	Result<?> save(WccCourseChapterForm courseChapterForm);
	
	/**
	 * 根据id获取课程章节详细信息
	 *
	 * @param courseChapterForm
	 * @return
	 */
	WccCourseChapterDto findById(WccCourseChapterForm courseChapterForm);
}
