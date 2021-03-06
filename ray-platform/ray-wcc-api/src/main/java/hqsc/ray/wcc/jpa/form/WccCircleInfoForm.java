package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.form.basic.BaseForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author yang
 */
@Getter
@Setter
@Accessors(chain = true)
public class WccCircleInfoForm extends BaseForm {
	
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
