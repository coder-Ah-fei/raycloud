package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.OrderDto;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.form.OrderForm;

import java.util.Map;

/**
 * 描述：
 *
 * @author yang
 * @date 2021-04-07
 */
public interface OrderService {
	
	/**
	 * 获取开通会员的配置列表
	 *
	 * @param orderForm
	 * @return
	 */
	PageMap<OrderDto> listOrders(OrderForm orderForm);
	
	/**
	 * 保存更新配置
	 *
	 * @param orderForm
	 * @return
	 */
	Result<Map<String, Object>> saveOpenVipOrder(OrderForm orderForm);
}
