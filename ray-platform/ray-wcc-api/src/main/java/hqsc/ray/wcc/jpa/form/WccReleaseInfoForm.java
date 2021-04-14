package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.form.basic.BaseForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.FetchType;

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
	
	@ApiModelProperty(value = "当前登录用户")
	private Long userId;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String titel;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;
	
	/**
	 * 发布类型(0提问1话题2文章3视频)
	 */
	@ApiModelProperty(value = "发布类型")
	private Long type;
	
	/**
	 * 所属用户
	 */
	@ApiModelProperty(value = "所属用户")
	private Long belongUserId;
	
	@ApiModelProperty(value = "所属圈子")
	private Long belongCircleId;
	
	
	@ApiModelProperty(value = "0 待审批  1审批通过 2审批未通过（临时处理）")
	private Integer approveStatus;
}
