package hqsc.ray.wcc.jpa.dto;

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
public class WccCelebrityInfoDto extends BasicDto {
	
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "用户id")
	private Long belongUserId;
	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	private String nickname;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private Long headPortraitId;
	/**
	 * 性别(0女1男)
	 */
	@ApiModelProperty(value = "性别(0女1男)")
	private Integer gender;
	/**
	 * 带货领域
	 */
	@ApiModelProperty(value = "带货领域")
	private String scope;
	/**
	 * 所属平台
	 */
	@ApiModelProperty(value = "所属平台")
	private String platform;
	/**
	 * 所属机构
	 */
	@ApiModelProperty(value = "所属机构")
	private Long organizationId;
	@ApiModelProperty(value = "所属MCN机构")
	private Long mcnInfoId;
	private String mcnName;
	
	/**
	 * 现居住地
	 */
	@ApiModelProperty(value = "现居住地")
	private String currentAddress;
	
	/**
	 * 红人标签
	 */
	@ApiModelProperty(value = "红人标签")
	private String tag;
	
	/**
	 * 等级
	 */
	@ApiModelProperty(value = "等级")
	private String level;
	/**
	 * 年限
	 */
	@ApiModelProperty(value = "年限")
	private String yearsLimit;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "年龄")
	private String age;
	/**
	 * 公会
	 */
	@ApiModelProperty(value = "公会")
	private String laborUnion;
	/**
	 * 简介
	 */
	@ApiModelProperty(value = "简介")
	private String state;
	/**
	 * 直播带货报价
	 */
	@ApiModelProperty(value = "直播带货报价")
	private BigDecimal livePrice;
	/**
	 * 短视频报价
	 */
	@ApiModelProperty(value = "短视频报价")
	private BigDecimal video;
	
	/**
	 * 粉丝数量
	 */
	@ApiModelProperty(value = "粉丝数量")
	private Integer fans;
}
