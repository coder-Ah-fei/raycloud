package hqsc.ray.wcc.jpa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 描述：
 *
 * @author Administrator
 */
@Getter
@Setter
@Accessors(chain = true)
public class WccMcnInfoDto extends BasicDto {
	
	private Long id;
	/**
	 * 机构图标
	 */
	@ApiModelProperty(value = "机构图标")
	private Long iconId;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String oname;
	/**
	 * 机构坐标
	 */
	@ApiModelProperty(value = "机构坐标")
	private String address;
	/**
	 * 领域
	 */
	@ApiModelProperty(value = "领域")
	private String field;
	
	/**
	 * 粉丝数量
	 */
	@ApiModelProperty(value = "粉丝数量")
	private Integer fansCount;
	
	/**
	 * 红人数量
	 */
	@ApiModelProperty(value = "红人数量")
	private Integer celebrityCount;
	
	/**
	 * 全称
	 */
	@ApiModelProperty(value = "全称")
	private String fullName;
	
	/**
	 * 结算方式
	 */
	@ApiModelProperty(value = "结算方式")
	private String settleType;
	
	/**
	 * 平台网址
	 */
	@ApiModelProperty(value = "平台网址")
	private String internetSite;
	
	/**
	 * 平台介绍
	 */
	@ApiModelProperty(value = "平台介绍")
	private String summary;
}
