package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.common.enums.BannerPosition;
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
public class WccBannerForm extends BaseForm {
	@ApiModelProperty(value = "banner图位置")
	private BannerPosition bannerPosition;
	
	@ApiModelProperty(value = "主键id")
	private Long id;
	
	@ApiModelProperty(value = "图片")
	private Long resourceId;
	
	@ApiModelProperty(value = "banner图名称")
	private String bannerName;
	
	@ApiModelProperty(value = "banner图描述")
	private String depict;
	
	@ApiModelProperty(value = "点击banner图访问的地址")
	private String url;
	
	@ApiModelProperty(value = "排序  从大到小， 越大越靠前")
	private Integer sort;
	
}
