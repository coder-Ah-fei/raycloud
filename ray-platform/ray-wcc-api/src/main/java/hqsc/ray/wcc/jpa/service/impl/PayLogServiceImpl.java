package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.util.Abacus;
import hqsc.ray.core.common.util.UUIDUtil;
import hqsc.ray.wcc.jpa.common.enums.OrderType;
import hqsc.ray.wcc.jpa.common.enums.PayMode;
import hqsc.ray.wcc.jpa.dto.wechatPay.UnifiedOrderDto;
import hqsc.ray.wcc.jpa.entity.Order;
import hqsc.ray.wcc.jpa.entity.OrderMember;
import hqsc.ray.wcc.jpa.entity.PayLog;
import hqsc.ray.wcc.jpa.form.PayLogForm;
import hqsc.ray.wcc.jpa.form.wechatPay.UnifiedOrderForm;
import hqsc.ray.wcc.jpa.form.wechatPay.UnifiedOrderForm.AmountDTO;
import hqsc.ray.wcc.jpa.form.wechatPay.UnifiedOrderForm.PayerDTO;
import hqsc.ray.wcc.jpa.repository.OrderRepository;
import hqsc.ray.wcc.jpa.repository.PayLogRepository;
import hqsc.ray.wcc.jpa.service.PayLogService;
import hqsc.ray.wcc.jpa.service.WechatPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-04-07
 */
@Service
@RequiredArgsConstructor
public class PayLogServiceImpl implements PayLogService {
	
	private final WechatPayService wechatPayService;
	private final PayLogRepository payLogRepository;
	private final PrivateKey wechatPrivateKey;
	private final OrderRepository orderRepository;
	
	@Value("${wechat.appid}")
	private String appid;
	
	/**
	 * 生成微信支付的统一下单（生成PayLog）
	 *
	 * @param payLogForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(PayLogForm payLogForm) {
		Optional<Order> orderOptional = orderRepository.findById(payLogForm.getOrderId());
		if (!orderOptional.isPresent()) {
			return Result.fail("订单不存在");
		}
		Order order = orderOptional.get();
		
		
		PayLog payLog = payLogRepository.findByPrePayId(payLogForm.getPrePayId());
		/**
		 *如果从数据库查询为null
		 * 或者 此条数据已经支付失败
		 * 或者 此条数据创建时间和当前时间相差1小时59分(微信支付的prePayId有效期为2小时)
		 * 则重新生成paylog
		 */
		if (payLog == null
				|| payLog.getPayStatus() == -1
				|| (System.currentTimeMillis() - payLog.getCreationDate().getTime() > (1000 * 60 * 60 * 2 - 60000))
		) {
			payLog = new PayLog();
			payLog.setOrder(order);
			payLog.setOrderWechatCode(UUIDUtil.longUuid());
			payLog.setPrePayId("");
			payLog.setPayPrice(order.getOrderPrice());
			payLog.setPayerOpenid(order.getJpaWccUser().getWechatUnionId());
			payLog.setPayMode(PayMode.WECHAT_PAY);
			payLog.setPayStatus(0);
			payLog.setStatus(1);
			payLog.setIsDelete(0);
			UnifiedOrderDto unifiedOrderDto = null;
			if (OrderType.OPEN_MEMBER_ORDER.equals(order.getOrderType())) {
				UnifiedOrderForm unifiedOrderForm = wechatUnifiedOrder(order, order.getOrderMember(), payLog);
				unifiedOrderDto = wechatPayService.unifiedOrder(unifiedOrderForm);
				
			}
			if (unifiedOrderDto == null) {
				return Result.fail("微信支付统一下单出错");
			}
			payLog.setPrePayId(unifiedOrderDto.getPrepay_id());
			payLog = payLogRepository.save(payLog);
		}
		
		//组装调起小程序支付的参数
		Map<String, Object> map = new HashMap<>();
		Long timeMillis = System.currentTimeMillis();
		timeMillis = timeMillis / 1000;
		String timeStamp = timeMillis.toString();
		String nonceStr = UUIDUtil.longUuid();
		String packageStr = "prepay_id=" + payLog.getPrePayId();
		map.put("prePayId", payLog.getPrePayId());
		map.put("timeStamp", timeStamp);
		map.put("nonceStr", nonceStr);
		map.put("package", packageStr);
		map.put("signType", "RSA");
		
		String str = appid + "\n" +
				timeStamp + "\n" +
				nonceStr + "\n" +
				packageStr + "\n";
		
		map.put("paySign", sign(str.getBytes(StandardCharsets.UTF_8)));
		
		return Result.data(map);
	}
	
	/**
	 * 对调起微信小程序支付的参数进行签名
	 *
	 * @param message
	 * @return
	 */
	String sign(byte[] message) {
		Signature sign = null;
		byte[] signArray = null;
		try {
			sign = Signature.getInstance("SHA256withRSA");
			sign.initSign(wechatPrivateKey);
			sign.update(message);
			signArray = sign.sign();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(signArray);
	}
	
	/**
	 * 微信统一下单参数组装
	 *
	 * @param order
	 * @param orderMember
	 */
	private UnifiedOrderForm wechatUnifiedOrder(Order order, OrderMember orderMember, PayLog payLog) {
		UnifiedOrderForm unifiedOrderForm = new UnifiedOrderForm();
		unifiedOrderForm.setDescription("开通红人圈" + orderMember.getSettingName() + "会员");
		unifiedOrderForm.setOut_trade_no(payLog.getOrderWechatCode());
		AmountDTO amountDTO = new AmountDTO();
		amountDTO.setTotal(Abacus.multiply(order.getOrderPrice(), 100).intValue());
		unifiedOrderForm.setAmount(amountDTO);
		PayerDTO payerDTO = new PayerDTO();
		payerDTO.setOpenid(order.getJpaWccUser().getWechatUnionId());
		unifiedOrderForm.setPayer(payerDTO);
		return unifiedOrderForm;
	}
}
