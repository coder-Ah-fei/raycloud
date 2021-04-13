package hqsc.ray.wcc.jpa.service.impl;

import com.sun.istack.Nullable;
import hqsc.ray.core.common.util.DateUtil;
import hqsc.ray.wcc.jpa.common.enums.PaymentMode;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.entity.MemberInfo;
import hqsc.ray.wcc.jpa.entity.OrderMember;
import hqsc.ray.wcc.jpa.repository.MemberInfoRepository;
import hqsc.ray.wcc.jpa.repository.OrderMemberRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-04-13
 */
@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {
	
	private final MemberInfoRepository memberInfoRepository;
	private final WccUserRepository userRepository;
	private final OrderMemberRepository orderMemberRepository;
	
	/**
	 * 开通会员
	 *
	 * @param jpaWccUser
	 * @param orderMember
	 */
	@Override
	public void openVip(JpaWccUser jpaWccUser, OrderMember orderMember) {
		// 给用户开通会员
		Optional<JpaWccUser> userOptional = userRepository.findById(jpaWccUser.getId());
		if (!userOptional.isPresent()) {
			throw new RuntimeException("会员没有开通成功, 用户不存在");
		}
		jpaWccUser = userOptional.get();
		Optional<OrderMember> orderMemberOptional = orderMemberRepository.findById(orderMember.getId());
		if (!orderMemberOptional.isPresent()) {
			throw new RuntimeException("会员没有开通成功， 用户开通订单不存在");
		}
		orderMember = orderMemberOptional.get();
		MemberInfo memberInfo = jpaWccUser.getMemberInfo();
		if (jpaWccUser.getMember() == null || jpaWccUser.getMember() == 0) {
			// 从来没开过会员的话，新建一个
			if (memberInfo == null) {
				memberInfo = new MemberInfo();
				memberInfo.setWccUser(jpaWccUser);
				memberInfo.setStatus(1);
				memberInfo.setIsDelete(0);
			}
			LocalDateTime startDateTime = LocalDateTime.now();
//				LocalDateTime startDateTime = DateUtil.localDateTimeOfDayStart(startDateTime);
			memberInfo.setStartDataTime(startDateTime.withNano(0));
			memberInfo.setEndDataTime(getMemberEndDateTime(startDateTime, orderMember));
			
		}
		if (jpaWccUser.getMember() != null && jpaWccUser.getMember() == 1) {
			LocalDateTime startDateTime = DateUtil.offset(memberInfo.getEndDataTime(), 1, DateUtil.SECOND);
//				LocalDateTime startDateTime = DateUtil.localDateTimeOfDayStart(startDateTime);
			memberInfo.setEndDataTime(getMemberEndDateTime(startDateTime, orderMember));
		}
		memberInfo.setPaymentMode(orderMember.getPaymentMode());
		memberInfo.setOrderMember(orderMember);
		memberInfoRepository.save(memberInfo);
		
		jpaWccUser.setMember(1);
		userRepository.save(jpaWccUser);
	}
	
	/**
	 * 开通会员，计算会员的结束日期
	 *
	 * @param startDateTime 本次支付成功后新的会员的开始日期（此日期只用于计算，没有在数据库中记录）
	 * @param orderMember
	 * @return
	 */
	@Nullable
	private LocalDateTime getMemberEndDateTime(LocalDateTime startDateTime, OrderMember orderMember) {
		LocalDateTime endDateTime = DateUtil.offset(startDateTime, -1, DateUtil.SECOND);
		// 按自然月
		if (orderMember.getValidityType() == 0) {
			if (PaymentMode.Monthly.equals(orderMember.getPaymentMode())) {
				return DateUtil.offset(endDateTime, 1, DateUtil.MONTH);
			}
			if (PaymentMode.Quarterly.equals(orderMember.getPaymentMode())) {
				return DateUtil.offset(endDateTime, 3, DateUtil.MONTH);
			}
			if (PaymentMode.Yearly.equals(orderMember.getPaymentMode())) {
				return DateUtil.offset(endDateTime, 12, DateUtil.MONTH);
			}
		}
		// 按天数
		if (orderMember.getValidityType() == 1) {
			return DateUtil.offset(endDateTime, orderMember.getDayNum() - 1, DateUtil.DAY);
		}
		return null;
	}
}
