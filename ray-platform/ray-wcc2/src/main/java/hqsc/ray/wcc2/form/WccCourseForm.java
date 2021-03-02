package hqsc.ray.wcc2.form;

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
public class WccCourseForm extends BaseForm {
	
	/**
	 * 当前登录用户
	 */
	@ApiModelProperty(value = "用户id")
	private Long userId;
	
	@ApiModelProperty(value = "课程id 主键")
	private Long wccCourseId;
	
	@ApiModelProperty(value = "是否热门课程(1是 0否)")
	private Integer isHot;
	
	@ApiModelProperty(value = "是否推荐课程(1是 0否)")
	private Integer isRecommend;
	
	
}
