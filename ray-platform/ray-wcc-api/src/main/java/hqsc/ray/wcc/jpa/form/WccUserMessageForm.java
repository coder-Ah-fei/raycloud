package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.form.basic.BaseForm;
import io.swagger.annotations.ApiModelProperty;

/**
 * 描述：
 *
 * @author Administrator
 */
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class WccUserMessageForm extends BaseForm {
	
	@ApiModelProperty(value = "当前登录用户的id")
	private Long userId;
	
	@ApiModelProperty(value = "消息类型(0评论1回答2赞同3系统消息)")
	private Integer messageType;
}
