package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccUserDto;
import hqsc.ray.wcc.jpa.form.WccUserConcernForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserConcernService {
	
	/**
	 * 获取数据
	 *
	 * @param wccUserConcernForm
	 * @return ResultMap
	 */
	PageMap<WccUserDto> listWccUserConcerns(WccUserConcernForm wccUserConcernForm);
	
	/**
	 * 获取关注数量
	 *
	 * @param userId
	 * @param belongUserId
	 * @return
	 */
	Long countByJpaWccUserIdAndBelongUserId(Long userId, Long belongUserId);
}
