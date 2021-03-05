package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.form.basic.BaseForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class WccAuthCenterForm extends BaseForm {
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long wccAuthCenterId;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long userId;
	
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;
	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	private String identityCard;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String phoneNumber;
	
	@ApiModelProperty(value = "邮寄地址（城市）")
	private String postAddress;
	
	@ApiModelProperty(value = "邮寄地址（具体地址）")
	private String postAddressDetail;
	
	@ApiModelProperty(value = "直播平台")
	private String livePlatform;
	
	@ApiModelProperty(value = "直播类型")
	private String liveType;
	
	/**
	 * 身份证正面照
	 */
	@ApiModelProperty(value = "身份证正面照")
	private Long frontId;
	/**
	 * 身份证反面照
	 */
	@ApiModelProperty(value = "身份证反面照")
	private Long backId;
	
	/**
	 * 2寸证件照
	 */
	@ApiModelProperty(value = "2寸证件照")
	private Long idPhotoId;
}
