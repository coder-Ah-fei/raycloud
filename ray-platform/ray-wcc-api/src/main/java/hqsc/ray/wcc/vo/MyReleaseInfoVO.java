package hqsc.ray.wcc.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import hqsc.ray.wcc.entity.WccReleaseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "MyQuestionVO对象", description = "我的提问VO")
public class MyReleaseInfoVO extends WccReleaseInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "点赞数")
	private Integer praiseCount;
	
	@ApiModelProperty(value = "评论数")
	private Integer commentCount;
	
	@ApiModelProperty(value = "用户姓名")
	private String name;
	
	@ApiModelProperty(value = "昵称")
	private String nickname;
	
	@ApiModelProperty(value = "微信头像地址")
	private String wechatHeadPortraitAddress;
	
	@ApiModelProperty(value = "当前用户最新的评论/回答时间")
	private String responseDetailsCreateDateNewest;
	
}
