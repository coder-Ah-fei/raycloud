package hqsc.ray.wcc.jpa.service.impl;

import com.alibaba.fastjson.JSON;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.util.Abacus;
import hqsc.ray.core.common.util.UUIDUtil;
import hqsc.ray.wcc.jpa.common.enums.OrderStatus;
import hqsc.ray.wcc.jpa.common.enums.OrderType;
import hqsc.ray.wcc.jpa.common.enums.PayMode;
import hqsc.ray.wcc.jpa.dto.OrderDto;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.wechatPay.NotifyDto;
import hqsc.ray.wcc.jpa.entity.*;
import hqsc.ray.wcc.jpa.form.OrderForm;
import hqsc.ray.wcc.jpa.repository.*;
import hqsc.ray.wcc.jpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-04-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	private final WccUserRepository userRepository;
	private final OpenVipConfigurationRepository openVipConfigurationRepository;
	private final OrderMemberRepository orderMemberRepository;
	private final PayLogRepository payLogRepository;
	
	@Value("${wechat.api_v3_key}")
	private String apiV3Key;
	
	/**
	 * 获取开通会员的配置列表
	 *
	 * @param orderForm
	 * @return
	 */
	@Override
	public PageMap<OrderDto> listOrders(OrderForm orderForm) {
		Specification<Order> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
//				if (!StringUtils.empty(articleForm.getSectionName())) {
//					Join<Object, Object> section = root.join("section");
//					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
//				}
			
			if (orderForm.getStatus() != null) {
				pr.add(criteriaBuilder.equal(root.get("status"), orderForm.getStatus()));
			}
			if (orderForm.getIsDelete() != null) {
				pr.add(criteriaBuilder.equal(root.get("isDelete"), orderForm.getIsDelete()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sort")));
			return criteriaQuery.getRestriction();
		};
		List<Order> orderList;
		Long count;
		if (orderForm.getPageNow() == -1) {
			orderList = orderRepository.findAll(specification);
			count = Long.valueOf(orderList.size());
		} else {
			Pageable pageable = PageRequest.of(orderForm.getPageNow() - 1, orderForm.getPageSize());
			Page<Order> openMembershipConfigurationPage = orderRepository.findAll(specification, pageable);
			orderList = openMembershipConfigurationPage.getContent();
			count = openMembershipConfigurationPage.getTotalElements();
		}
		List<OrderDto> list = new ArrayList<>();
		OrderDto orderDto;
		for (Order order : orderList) {
			orderDto = new OrderDto();
			BeanUtils.copyProperties(order, orderDto);
			list.add(orderDto);
		}
		return PageMap.of(count, list);
	}
	
	/**
	 * 保存更新配置
	 *
	 * @param orderForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<Map<String, Object>> saveOpenVipOrder(OrderForm orderForm) {
		
		Optional<JpaWccUser> userOptional = userRepository.findById(orderForm.getJpaWccUserId());
		if (!userOptional.isPresent()) {
			return Result.fail("请登录");
		}
		JpaWccUser user = userOptional.get();
		Optional<OpenVipConfiguration> openVipConfigurationOptional = openVipConfigurationRepository.findById(orderForm.getOpenVipConfigurationId());
		if (!openVipConfigurationOptional.isPresent()) {
			return Result.fail("没有找到开通会员配置");
		}
		OpenVipConfiguration openVipConfiguration = openVipConfigurationOptional.get();
		
		Order order = new Order();
		order.setJpaWccUser(user)
				.setOrderCode("OV" + UUIDUtil.longUuid())
				.setOrderType(OrderType.OPEN_MEMBER_ORDER)
				.setFinishDataTime(LocalDateTime.now())
				.setPayDataTime(LocalDateTime.now())
				.setPay(false)
				.setPayMode(PayMode.WECHAT_PAY)
				.setPayPrice(BigDecimal.ZERO)
				.setOrderPrice(openVipConfiguration.getPresentPrice())
				.setOrderStatus(OrderStatus.ordered)
				.setStatus(1)
				.setIsDelete(0);
		orderRepository.save(order);
		
		OrderMember orderMember = new OrderMember();
		orderMember.setSettingName(openVipConfiguration.getSettingName());
		orderMember.setPaymentMode(openVipConfiguration.getPaymentMode());
		orderMember.setOriginalPrice(openVipConfiguration.getOriginalPrice());
		orderMember.setPresentPrice(openVipConfiguration.getPresentPrice());
		orderMember.setNextPrice(openVipConfiguration.getNextPrice());
		orderMember.setSort(openVipConfiguration.getSort());
		
		orderMember.setOrder(order);
		orderMember.setOpenVipConfiguration(openVipConfiguration);
		orderMember.setStatus(1);
		orderMember.setIsDelete(0);
		orderMember = orderMemberRepository.save(orderMember);
		
		Map<String, Object> map = new HashMap<>(3);
		map.put("orderId", order.getId());
		return Result.data(map);
	}
	
	/**
	 * 收到微信支付通知之后
	 *
	 * @param notify
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void getWechatPayNotifyAfter(String notify) {
		log.info("接收到微信支付通知【{}】", notify);
		NotifyDto notifyDto = JSON.parseObject(notify, NotifyDto.class);
		if (!"TRANSACTION.SUCCESS".equals(notifyDto.getEvent_type())) {
			log.info("接收到微信支付通知【但是支付没成功】");
			return;
		}
		AesUtil decryptor = new AesUtil(apiV3Key.getBytes(StandardCharsets.UTF_8));
		try {
			String resourceStr = decryptor.decryptToString(
					notifyDto.getResource().getAssociated_data().getBytes(StandardCharsets.UTF_8),
					notifyDto.getResource().getNonce().getBytes(StandardCharsets.UTF_8),
					notifyDto.getResource().getCiphertext());
			log.info("接收到微信支付通知,解密后的数据【{}】", resourceStr);
			NotifyDto.Resource resource = JSON.parseObject(resourceStr, NotifyDto.Resource.class);
			
			// 更新支付日志状态
			PayLog payLog = payLogRepository.findByOrderWechatCode(resource.getOut_trade_no());
			if (payLog == null) {
				log.error("接收到微信支付通知之后没有找到支付日志");
				return;
			}
			payLog.setPayStatus(1);
			payLog = payLogRepository.save(payLog);
			
			// 更新订单状态
			Order order = payLog.getOrder();
			order.setPay(true);
			order.setPayDataTime(LocalDateTime.now());
			order.setFinishDataTime(LocalDateTime.now());
			order.setPayPrice(Abacus.divide(new BigDecimal(resource.getAmount().getTotal()), 100));
			order.setOrderStatus(OrderStatus.finish);
			orderRepository.save(order);
			
			// 给用户开通会员
			//TODO
			fdasfdsafdsa
			JpaWccUser jpaWccUser = order.getJpaWccUser();
			OrderMember orderMember = order.getOrderMember();
			MemberInfo memberInfo = jpaWccUser.getMemberInfo();
			if (memberInfo == null) {
				memberInfo = new MemberInfo();
				memberInfo.setWccUser(jpaWccUser);
				LocalDateTime now = LocalDateTime.now();
				memberInfo.setStartDataTime(now);
				
				// 按自然月
				if (orderMember.getValidityType() == 0) {
				
				}
				// 按天数
				if (orderMember.getValidityType() == 1) {
				
				}
				
				memberInfo.setEndDataTime(LocalDateTime.now());
				memberInfo.setPaymentMode(orderMember.getPaymentMode());
				memberInfo.setOrderMember(orderMember);
				memberInfo.setStatus(1);
				memberInfo.setIsDelete(0);
			}
			
			
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
