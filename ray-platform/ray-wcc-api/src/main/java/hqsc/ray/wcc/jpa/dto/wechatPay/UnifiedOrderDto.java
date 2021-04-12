package hqsc.ray.wcc.jpa.dto.wechatPay;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接收微信统一下单返回参数
 *
 * @author yang
 * @date 2021-04-08
 */
@NoArgsConstructor
@Data
public class UnifiedOrderDto {
	
	/**
	 * prepay_id : wx09103815988778e725137fb08a76920000
	 */
	
	@JSONField(name = "prepay_id")
	private String prepay_id;
}
