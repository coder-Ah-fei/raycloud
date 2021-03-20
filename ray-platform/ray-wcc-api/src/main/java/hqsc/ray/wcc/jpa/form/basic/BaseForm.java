package hqsc.ray.wcc.jpa.form.basic;

import hqsc.ray.wcc.jpa.common.enums.SaveType;
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
public class BaseForm extends PageForm {
	/**
	 * add 保存
	 * edit 更新
	 */
	private SaveType saveType;
	/**
	 * 状态（1正常 0禁用）
	 */
	@ApiModelProperty(value = "状态（1正常 0禁用）")
	private Integer status;
	/**
	 * 1 删除  0正常
	 */
	@ApiModelProperty(value = "1 删除  0正常")
	private Integer isDelete;
}
