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
public class WccPraiseFavoriteForm extends BaseForm {
	@ApiModelProperty(value = "点赞用户")
	private Long userId;
	/**
	 * 类型(0点赞1收藏)
	 */
	@ApiModelProperty(value = "类型(0点赞1收藏)")
	private Integer type;
	/**
	 * 点赞收藏类型(0回复1提问2文章3话题4视频5课程)
	 */
	@ApiModelProperty(value = "点赞收藏类型(0回复1提问2文章3话题4视频5课程)")
	private Integer praiseFavoriteType;
	/**
	 * 所属id
	 */
	@ApiModelProperty(value = "所属id")
	private Long belongId;
}
