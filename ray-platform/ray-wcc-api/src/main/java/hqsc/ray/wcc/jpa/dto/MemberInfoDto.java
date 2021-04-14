package hqsc.ray.wcc.jpa.dto;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-04-14
 */

import hqsc.ray.wcc.jpa.common.enums.PaymentMode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class MemberInfoDto extends BasicDto {
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long id;
	
	@ApiModelProperty(value = "用户")
	private Long userId;
	
	@ApiModelProperty(value = "会员开始日期")
	private LocalDateTime startDataTime;
	
	@ApiModelProperty(value = "会员结束时间")
	private LocalDateTime endDataTime;
	
	
	@ApiModelProperty(value = "续费模式")
	private PaymentMode paymentMode;
	private String paymentModeText;
}
