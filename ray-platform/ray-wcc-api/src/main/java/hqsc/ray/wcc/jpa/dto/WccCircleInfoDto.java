package hqsc.ray.wcc.jpa.dto;

/**
 * 描述：
 *
 * @author Administrator
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class WccCircleInfoDto extends BasicDto {
	
	@ApiModelProperty(value = "主键id")
	private Long id;
	
	
	/**
	 * 圈子名称
	 */
	@ApiModelProperty(value = "圈子名称")
	private String circleName;
	/**
	 * 圈子图片
	 */
	@ApiModelProperty(value = "圈子图片")
	private Long circleImgId;
	
	
	/**
	 * 圈子简介
	 */
	@ApiModelProperty(value = "圈子简介")
	private String circleSynopsis;
	
}
