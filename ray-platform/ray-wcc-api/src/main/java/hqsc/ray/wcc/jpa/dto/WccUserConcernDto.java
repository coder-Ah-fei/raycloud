package hqsc.ray.wcc.jpa.dto;

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
public class WccUserConcernDto {
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long id;
	
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long jpaWccUserId;
	
	/**
	 * 所属id(被关注的用户)
	 */
	@ApiModelProperty(value = "所属id")
	private Long belongUserId;
	
	@ApiModelProperty(value = "所属id")
	private String nickname;
	
	@ApiModelProperty(value = "微信头像地址")
	private String wechatHeadPortraitAddress;
	
	/**
	 * 访问次数
	 */
	@ApiModelProperty(value = "访问次数")
	private Integer accessCount;
}
