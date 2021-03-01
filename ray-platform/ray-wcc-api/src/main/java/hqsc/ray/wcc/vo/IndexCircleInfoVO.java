package hqsc.ray.wcc.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author yang
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IndexCircleInfoVO对象", description = "首页圈子VO")
public class IndexCircleInfoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "当前登录用户是否加入圈子，>0 为加入")
	private Integer joined;
	
	@ApiModelProperty(value = "圈子里面的人数")
	private Integer peopleCount;
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long circleId;
	/**
	 * 圈子名称
	 */
	@ApiModelProperty(value = "圈子名称")
	private String circleName;
	/**
	 * 圈子图片
	 */
	@ApiModelProperty(value = "圈子图片")
	private Long circleImg;
	/**
	 * 圈子简介
	 */
	@ApiModelProperty(value = "圈子简介")
	private String circleSynopsis;
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
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createdBy;
	/**
	 * 创建人姓名
	 */
	@ApiModelProperty(value = "创建人姓名")
	private String createdByUser;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime creationDate;
	/**
	 * 修改人姓名
	 */
	@ApiModelProperty(value = "修改人姓名")
	private String lastUpdatedByUser;
	/**
	 * 最后更新人
	 */
	@ApiModelProperty(value = "最后更新人")
	private String lastUpdatedBy;
	/**
	 * 最后更新时间
	 */
	@ApiModelProperty(value = "最后更新时间")
	private LocalDateTime lastUpdateDate;
}
