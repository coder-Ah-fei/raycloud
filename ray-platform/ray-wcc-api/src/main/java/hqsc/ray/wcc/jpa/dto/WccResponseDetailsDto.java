package hqsc.ray.wcc.jpa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author Administrator
 */
@Getter
@Setter
@Accessors(chain = true)
public class WccResponseDetailsDto {
	private Long id;
	/**
	 * 回答用户
	 */
	@ApiModelProperty(value = "回答用户")
	private Long userId;
	private String userNickname;
	/**
	 * 回答时间
	 */
	@ApiModelProperty(value = "回答时间")
	private LocalDateTime responseTime;
	private String responseTimeStr;
	/**
	 * 回答内容
	 */
	@ApiModelProperty(value = "回答内容")
	private String responseBody;
	/**
	 * 所属类型(0回复1提问2文章3话题4视频)
	 */
	@ApiModelProperty(value = "所属类型(0回复1提问2文章3话题4视频)")
	private Integer belongType;
	/**
	 * 所属id
	 */
	@ApiModelProperty(value = "所属id")
	private Long releaseInfoId;
	
	/**
	 * 上级id
	 */
	@ApiModelProperty(value = "上级id")
	private Long responseDetailsId;
	
	/**
	 * 收藏数
	 */
	@ApiModelProperty(value = "收藏数")
	private Integer favoriteCount;
}
