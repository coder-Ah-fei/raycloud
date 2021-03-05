package hqsc.ray.wcc.jpa.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 描述：
 *
 * @author Administrator
 */
@Getter
@Setter
@Accessors(chain = true)
public class WccUserMessageDto {
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 消息类型(0评论1回答2赞同3系统消息)
	 */
	@ApiModelProperty(value = "消息类型(0评论1回答2赞同3系统消息)")
	private Integer messageType;
	/**
	 * 消息内容
	 */
	@ApiModelProperty(value = "消息内容")
	private String messageContent;
	/**
	 * 消息时间
	 */
	@ApiModelProperty(value = "消息时间")
	private Date messageTime;
	private String messageTimeStr;
	
	
	@ApiModelProperty(value = "1 已读  0 未读")
	private Integer isRead;
	
}
