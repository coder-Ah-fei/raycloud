package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.form.basic.BaseForm;
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
public class WccReleaseInfoForm extends BaseForm {
	
	private Long id;
	
	/**
	 * 所属用户
	 */
	@ApiModelProperty(value = "所属用户")
	private Long belongUserId;
	
	@ApiModelProperty(value = "所属圈子")
	private Long belongCircleId;
	
	/**
	 * 发布类型(0提问1话题2文章3视频)
	 */
	@ApiModelProperty(value = "发布类型")
	private Long type;
	
	@ApiModelProperty(value = "0 待审批  1审批通过 2审批未通过（临时处理）")
	private Integer approveStatus;
}
