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
public class WccUserForm extends BaseForm {
	
	private Long id;
	/**
	 * 红人标记
	 * 网红/自媒体标记
	 */
	@ApiModelProperty(value = "红人标记")
	private Integer celebrityFlag;
	
	/**
	 * 达人标记
	 */
	@ApiModelProperty(value = "达人标记")
	private Integer talentFlag;
	
	/**
	 * 明星/名人标记
	 */
	@ApiModelProperty(value = "明星/名人标记")
	private Integer starFlag;
}
