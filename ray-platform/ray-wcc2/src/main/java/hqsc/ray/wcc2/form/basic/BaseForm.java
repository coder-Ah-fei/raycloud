package hqsc.ray.wcc2.form.basic;

import hqsc.ray.wcc2.common.enums.SaveType;
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
	
}
