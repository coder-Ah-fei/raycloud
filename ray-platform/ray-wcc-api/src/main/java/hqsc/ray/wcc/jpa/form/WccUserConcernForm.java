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
public class WccUserConcernForm extends BaseForm {
	/**
	 * 所属id
	 */
	@ApiModelProperty(value = "当前登录用户")
	private Long userId;
	
	private Integer current;
	private Integer size;
}
