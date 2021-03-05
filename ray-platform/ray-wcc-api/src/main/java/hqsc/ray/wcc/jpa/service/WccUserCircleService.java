package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccUserCircleForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserCircleService {
	
	/**
	 * 获取数据
	 *
	 * @param wccUserCircleForm
	 * @return ResultMap
	 */
	ResultMap listWccUserCircles(WccUserCircleForm wccUserCircleForm);
	
	/**
	 * 取消加入圈子
	 *
	 * @param userId
	 * @param circleId
	 * @return
	 */
	Result cancelJoinCircle(Long userId, Long circleId);
}
