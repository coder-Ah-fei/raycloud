package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.entity.OrderCourse;
import hqsc.ray.wcc.jpa.form.WccUserPurchasedCourseForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface UserPurchasedCourseService {
	
	/**
	 * 获取数据
	 *
	 * @param wccUserPurchasedCourseForm
	 * @return ResultMap
	 */
	ResultMap listWccUserPurchasedCourses(WccUserPurchasedCourseForm wccUserPurchasedCourseForm);
	
	/**
	 * 下发课程
	 *
	 * @param jpaWccUser
	 * @param orderCourse
	 */
	void save(JpaWccUser jpaWccUser, OrderCourse orderCourse);
	
	/**
	 * 用户是否购买课程
	 *
	 * @param userId
	 * @param courseId
	 * @return
	 */
	Integer isBuyForUser(Long userId, Long courseId);
	
}
