package hqsc.ray.wcc.jpa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 表基础信息
 *
 * @author yang
 */
@Getter
@Setter
@Accessors(chain = true)
public class BasicDto {
	
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
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date creationDate;
	
	/**
	 * 最新的更新时间
	 */
	@ApiModelProperty(value = "最后更新时间")
	private Date lastUpdateDate;
	
	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建人")
	private String createdBy;
	/**
	 * 创建人姓名
	 */
	@ApiModelProperty(value = "创建人姓名")
	private String createdByUser;
	
	
	/**
	 * 最后的更新用户
	 */
	@ApiModelProperty(value = "最后更新人")
	private String lastUpdatedBy;
	
	
	/**
	 * 修改人姓名
	 */
	@ApiModelProperty(value = "修改人姓名")
	private String lastUpdatedByUser;
	
}
