package hqsc.ray.wcc2.form;

import hqsc.ray.wcc2.common.enums.BannerPosition;
import hqsc.ray.wcc2.form.basic.BaseForm;
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
public class WccBannerForm extends BaseForm {
	@ApiModelProperty(value = "banner图位置")
	private BannerPosition bannerPosition;
}
