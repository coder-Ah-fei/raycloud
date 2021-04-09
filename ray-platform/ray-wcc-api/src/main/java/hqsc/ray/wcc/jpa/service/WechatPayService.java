package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.wechatPay.UnifiedOrderDto;
import hqsc.ray.wcc.jpa.form.wechatPay.UnifiedOrderForm;

/**
 * 微信商户平台支付接口
 *
 * @author yang
 * @date 2021-04-08
 */
public interface WechatPayService {
	/**
	 * 微信统一下单
	 *
	 * @param unifiedOrderForm
	 * @return
	 */
	UnifiedOrderDto unifiedOrder(UnifiedOrderForm unifiedOrderForm);
}
