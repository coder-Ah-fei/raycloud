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
	/**
	 * 所属用户
	 */
	@ApiModelProperty(value = "所属用户")
	private Long belongUserId;
	
	/**
	 * 发布类型(0提问1话题2文章3视频)
	 */
	@ApiModelProperty(value = "发布类型")
	private Long type;
	
}
