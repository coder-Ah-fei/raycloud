package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccAuthCenterForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccAuthCenterService {
	
	/**
	 * 获取数据
	 *
	 * @param wccAuthCenterForm
	 * @return ResultMap
	 */
	ResultMap listWccAuthCenters(WccAuthCenterForm wccAuthCenterForm);
	
	/**
	 * 主播证报名
	 *
	 * @param wccAuthCenterForm
	 * @return
	 */
	ResultMap anchorInfoSubmit(WccAuthCenterForm wccAuthCenterForm);
	
	/**
	 * 判断用户是否已经主播报名成功
	 *
	 * @param userId
	 * @return
	 */
	ResultMap<?> isAnchorInfoSubmit(Long userId);
}
