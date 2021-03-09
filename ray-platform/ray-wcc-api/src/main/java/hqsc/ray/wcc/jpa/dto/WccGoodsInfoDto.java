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
public class WccGoodsInfoDto {
	
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 所属品牌方/商家
	 */
	@ApiModelProperty(value = "所属品牌方/商家")
	private Long merchantBrandId;
	private String merchantBrandName;
	/**
	 * 主推商品名称
	 */
	@ApiModelProperty(value = "主推商品名称")
	private String mainProduct;
	/**
	 * 图片
	 */
	@ApiModelProperty(value = "图片")
	private Long imgId;
	/**
	 * 供货价
	 */
	@ApiModelProperty(value = "供货价")
	private BigDecimal supplyPrice;
	/**
	 * 原价
	 */
	@ApiModelProperty(value = "原价")
	private BigDecimal originalPrice;
	/**
	 * 产品文件
	 */
	@ApiModelProperty(value = "产品文件")
	private Long productFileId;
	
	
	@ApiModelProperty(value = "产品优势")
	private String productSuperiority;
}
