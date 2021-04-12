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
public class UserAttachmentForm extends BaseForm {
	@ApiModelProperty(value = "主键id")
	private Long id;
	@ApiModelProperty(value = "用户id")
	private Long userId;
	@ApiModelProperty(value = "附件id SysAttachmentId")
	private Long attachmentId;
}
