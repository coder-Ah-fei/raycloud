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
public class WccReleaseInfoDto {
	private Long id;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String titel;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;
	
	/**
	 * 附件id
	 */
	@ApiModelProperty(value = "附件id")
	private Long sysAttachmentId;
	@ApiModelProperty(value = "视频文件的截图路径")
	private String videoScreenshotPath;
	@ApiModelProperty(value = "视频文件的hls路径")
	private String videoHlsPath;
	
	/**
	 * 发布类型(0提问1话题2文章3视频)
	 */
	@ApiModelProperty(value = "发布类型")
	private Long type;
	/**
	 * 所属用户
	 */
	@ApiModelProperty(value = "所属用户")
	private Long belongUserId;
	private String belongUserNickname;
	private String wechatHeadPortraitAddress;
	/**
	 * 所属圈子
	 */
	@ApiModelProperty(value = "所属圈子")
	private Long belongCircleId;
	private String circleName;
	
	@ApiModelProperty(value = "点赞数")
	private Integer praiseCount;
	
	@ApiModelProperty(value = "评论数")
	private Integer commentCount;
	
	@ApiModelProperty(value = "收藏，0 没有收藏，1 收藏")
	private Integer favoritesCount;
	
	@ApiModelProperty(value = "关注，0 没有关注，1 关注")
	private Integer concernCount;
	/**
	 * 1 删除  0正常
	 */
	@ApiModelProperty(value = "0 待审批  1审批通过 2审批未通过（临时处理）")
	private Integer approveStatus;
	
	private String creationDateTimeStr;
}
