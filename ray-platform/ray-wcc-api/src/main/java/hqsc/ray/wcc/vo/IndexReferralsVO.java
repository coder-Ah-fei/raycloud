package hqsc.ray.wcc.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IndexReferralsVO对象", description = "首页推荐VO")
public class IndexReferralsVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户id")
	private Long userId;
	
	@ApiModelProperty(value = "头像附件id")
	private Long attId;
	/**
	 * 微信头像地址
	 */
	@ApiModelProperty(value = "微信头像地址")
	@TableField("WECHAT_HEAD_PORTRAIT_ADDRESS")
	private String wechatHeadPortraitAddress;
	
	@ApiModelProperty(value = "主键id")
	private Long releaseInfoId;
	
	@ApiModelProperty(value = "昵称")
	private String nickname;
	
	@ApiModelProperty(value = "会员（0非会员1会员）")
	private Integer member;
	
	@ApiModelProperty(value = "会员等级")
	private Integer memberRank;
	
	@ApiModelProperty(value = "发布类型(0提问1话题2文章3视频)")
	private Long type;
	
	@ApiModelProperty(value = "标题")
	private String titel;
	
	@ApiModelProperty(value = "内容")
	private String content;
	
	@ApiModelProperty(value = "附件id")
	private Long attachmentId;
	private String attachmentPath;
	
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime creationDate;
	private String creationDateStr;
	
	@ApiModelProperty(value = "点赞数")
	private Integer praiseCount;
	
	@ApiModelProperty(value = "评论数")
	private Integer commentCount;
	
	@ApiModelProperty(value = "当前登录用户是否关注，大于等于1为关注")
	private Integer concernCount;
	
	
	@ApiModelProperty(value = "视频文件的截图路径")
	private String videoScreenshotPath;
	@ApiModelProperty(value = "视频文件的hls路径")
	private String videoHlsPath;
	
}
