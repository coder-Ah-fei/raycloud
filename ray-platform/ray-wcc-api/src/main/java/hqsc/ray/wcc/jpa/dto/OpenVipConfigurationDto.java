package hqsc.ray.wcc.jpa.dto;

import hqsc.ray.wcc.jpa.common.enums.PaymentMode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-04-07
 */

@Getter
@Setter
@Accessors(chain = true)
public class OpenVipConfigurationDto {
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long id;
	@ApiModelProperty(value = "配置名称")
	private String settingName;
	@ApiModelProperty(value = "续费模式")
	private PaymentMode paymentMode;
	private String paymentModeText;
	
	
	@ApiModelProperty(value = "原价")
	private BigDecimal originalPrice;
	
	@ApiModelProperty(value = "现价")
	private BigDecimal presentPrice;
	
	@ApiModelProperty(value = "下次续费的价格")
	private BigDecimal nextPrice;
	
	
	@ApiModelProperty(value = "天数")
	private int dayNum;
	
	@ApiModelProperty(value = "会员有效期的计算方式（0，按自然月；1，按天数）")
	private int validityType;
	
	@ApiModelProperty(value = "排序")
	private int sort;
}
