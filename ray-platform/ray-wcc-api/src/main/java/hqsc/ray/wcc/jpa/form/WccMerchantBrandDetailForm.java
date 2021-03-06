package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.form.basic.BaseForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class WccMerchantBrandDetailForm extends BaseForm {
	@ApiModelProperty(value = "是否获取商品信息 true/false")
	private boolean getGoodsInfo;
}
