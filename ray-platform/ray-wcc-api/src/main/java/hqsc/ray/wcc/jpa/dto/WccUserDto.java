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
public class WccUserDto {
	@ApiModelProperty(value = "当前登录用户是否关注此用户 >0 为关注")
	private Long concernCount;
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long id;
	
	
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;
	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	private String nickname;
	/**
	 * 用户手机号
	 */
	@ApiModelProperty(value = "用户手机号")
	private String phone;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private Long jpaSysAttachmentId;
	
	/**
	 * 微信昵称
	 */
	@ApiModelProperty(value = "微信昵称")
	private String wechatNickname;
	/**
	 * 微信unionID
	 */
	@ApiModelProperty(value = "微信unionID")
	private String wechatUnionId;
	/**
	 * 微信头像地址
	 */
	@ApiModelProperty(value = "微信头像地址")
	private String wechatHeadPortraitAddress;
	/**
	 * 认证类型(0个人认证1企业认证)
	 */
	@ApiModelProperty(value = "认证类型(0个人认证1企业认证)")
	private Integer authType;
	/**
	 * 会员（0非会员1会员）
	 */
	@ApiModelProperty(value = "会员（0非会员1会员）")
	private Integer member;
	/**
	 * 会员等级
	 */
	@ApiModelProperty(value = "会员等级")
	private Integer memberRank;
	
	/**
	 * 会员等级
	 */
	@ApiModelProperty(value = "红人标记")
	private Integer celebrityFlag;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private Integer gender;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "年龄")
	private Integer age;
	/**
	 * 用户等级
	 */
	@ApiModelProperty(value = "用户等级")
	private Integer userRank;
	/**
	 * 用户简介
	 */
	@ApiModelProperty(value = "用户简介")
	private String userIntroduce;
	/**
	 * 状态（1正常 0禁用）
	 */
	@ApiModelProperty(value = "状态（1正常 0禁用）")
	private Integer status;
}
