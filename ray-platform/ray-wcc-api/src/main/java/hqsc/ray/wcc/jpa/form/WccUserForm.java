package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.common.enums.PaymentMode;
import hqsc.ray.wcc.jpa.form.basic.BaseForm;
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
public class WccUserForm extends BaseForm {
	
	private Long id;
	/**
	 * 红人标记
	 * 网红/自媒体标记
	 */
	@ApiModelProperty(value = "红人标记")
	private Integer celebrityFlag;
	
	/**
	 * 达人标记
	 */
	@ApiModelProperty(value = "达人标记")
	private Integer talentFlag;
	
	/**
	 * 明星/名人标记
	 */
	@ApiModelProperty(value = "明星/名人标记")
	private Integer starFlag;
	
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
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 盐
	 */
	@ApiModelProperty(value = "盐")
	private String salt;
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
	
	@ApiModelProperty(value = "会员开始时间")
	private String memberDateTimeStart;
	@ApiModelProperty(value = "会员结束时间")
	private String memberDateTimeEnd;
	@ApiModelProperty(value = "续费模式")
	private PaymentMode paymentMode;
}
