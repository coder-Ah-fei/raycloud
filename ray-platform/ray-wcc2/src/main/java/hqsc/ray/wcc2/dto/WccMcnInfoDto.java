package hqsc.ray.wcc2.dto;

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
public class WccMcnInfoDto {
	
	private Long id;
	/**
	 * 机构图标
	 */
	@ApiModelProperty(value = "机构图标")
	private Long headPortraitId;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String oName;
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
}
