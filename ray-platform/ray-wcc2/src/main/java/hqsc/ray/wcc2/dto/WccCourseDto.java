package hqsc.ray.wcc2.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 描述：
 *
 * @author Administrator
 */
@Getter
@Setter
@Accessors(chain = true)
public class WccCourseDto {
	
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 课程标题
	 */
	@ApiModelProperty(value = "课程标题")
	private String courseTitel;
	
	/**
	 * 课程附件
	 */
	@ApiModelProperty(value = "课程附件")
	private Long courseAttachment;
	
	
	@ApiModelProperty(value = "小图")
	private Long titlePicId;
	
	
	@ApiModelProperty(value = "banner图")
	private Long[] bannerIds;
	
	/**
	 * 课程类型(0系统课1进阶课)
	 */
	@ApiModelProperty(value = "课程类型(0系统课1进阶课)")
	private Integer courseType;
	/**
	 * 课程现价
	 */
	@ApiModelProperty(value = "课程现价")
	private BigDecimal currentPrice;
	/**
	 * 课程原价
	 */
	@ApiModelProperty(value = "课程原价")
	private BigDecimal originalPrice;
	/**
	 * 会员是否免费(0免费1收费)
	 */
	@ApiModelProperty(value = "会员是否免费(0免费1收费)")
	private Integer vipIsFree;
	/**
	 * 会员收费价格
	 */
	@ApiModelProperty(value = "会员收费价格")
	private BigDecimal vipPrice;
	/**
	 * 销售数量
	 */
	@ApiModelProperty(value = "销售数量")
	private Integer sellCount;
	/**
	 * 课程标题介绍
	 */
	@ApiModelProperty(value = "课程标题介绍")
	private String courseTitelIntroduce;
	/**
	 * 课程内容介绍
	 */
	@ApiModelProperty(value = "课程内容介绍")
	private String courseBodyIntroduce;
	/**
	 * 连载状态(0已完结1连载中)
	 */
	@ApiModelProperty(value = "连载状态(0已完结1连载中)")
	private Integer isSerial;
	
	@ApiModelProperty(value = "是否热门课程(1是 0否)")
	private Integer isHot;
	
	@ApiModelProperty(value = "是否推荐课程(1是 0否)")
	private Integer isRecommend;
}
