package hqsc.ray.wcc.jpa.form.basic;

import hqsc.ray.wcc.jpa.common.enums.SaveType;
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
