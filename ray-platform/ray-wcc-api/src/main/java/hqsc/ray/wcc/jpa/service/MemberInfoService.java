package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.entity.OrderMember;

/**
 * 描述：
 *
 * @author yang
 * @date 2021-04-13
 */
public interface MemberInfoService {
	
	/**
	 * 开通会员
	 *
	 * @param jpaWccUser
	 * @param orderMember
	 */
	void openVip(JpaWccUser jpaWccUser, OrderMember orderMember);
}
