package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.common.enums.PayMode;
import hqsc.ray.wcc.jpa.form.basic.BaseForm;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 描述：
 *
 * @author Administrator
 */
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PayLogForm extends BaseForm {
	/**
	 * 主键id
	 */
	private Long id;
	
	@ApiModelProperty(value = "订单")
	private Long orderId;
	
	@ApiModelProperty(value = "当前登录用户的id")
	private Long userId;
	
	@ApiModelProperty(value = "微信统一下单号")
	private String orderWechatCode;
	
	
	@ApiModelProperty(value = "预支付交易会话标识")
	private String prePayId;
	
	
	@ApiModelProperty(value = "支付金额")
	private BigDecimal payPrice;
	
	@ApiModelProperty(value = "支付人")
	private String payerOpenid;
	
	
	@ApiModelProperty(value = "支付方式")
	private PayMode payMode;
	
	@ApiModelProperty(value = "支付状态（1成功 0支付中 -1失败）")
	private Integer payStatus = 0;
	
}
