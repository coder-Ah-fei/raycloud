package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.form.PayLogForm;

/**
 * 描述：
 *
 * @author yang
 * @date 2021-04-07
 */
public interface PayLogService {
	
	
	/**
	 * 生成微信支付的统一下单（生成PayLog）
	 *
	 * @param payLogForm
	 * @return
	 */
	Result<?> save(PayLogForm payLogForm);
}
