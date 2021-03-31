package hqsc.ray.wcc.jpa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
@Getter
@Setter
@Accessors(chain = true)
public class WccMerchantBrandDetailDto extends BasicDto {
	private Long id;
	/**
	 * 品牌方/商家图标
	 */
	@ApiModelProperty(value = "品牌方/商家图标")
	private Long iconId;
	/**
	 * 品牌方/商家名称
	 */
	@ApiModelProperty(value = "品牌方/商家名称")
	private String name;
	/**
	 * 品牌方/商家联系方式
	 */
	@ApiModelProperty(value = "品牌方/商家联系方式")
	private String phone;
	/**
	 * 评分级别
	 */
	@ApiModelProperty(value = "评分级别")
	private Integer level;
	private String levelStr;
	/**
	 * 平台
	 */
	@ApiModelProperty(value = "平台")
	private String platform;
	/**
	 * 类目
	 */
	@ApiModelProperty(value = "类目")
	private String type;
	/**
	 * sku数量
	 */
	@ApiModelProperty(value = "sku数量")
	private Integer skuCount;
	/**
	 * 定位
	 */
	@ApiModelProperty(value = "定位")
	private String orient;
	/**
	 * 合作渠道
	 */
	@ApiModelProperty(value = "合作渠道")
	private String partnerChannel;
	/**
	 * 产品优势
	 */
	@ApiModelProperty(value = "产品优势")
	private String productSuperiority;
	/**
	 * 合作模式
	 */
	@ApiModelProperty(value = "合作模式")
	private String partnerModel;
	/**
	 * 独家优势
	 */
	@ApiModelProperty(value = "独家优势")
	private String exclusiveSuperiority;
	/**
	 * 推荐理由
	 */
	@ApiModelProperty(value = "推荐理由")
	private String introduce;
	/**
	 * 商家备注
	 */
	@ApiModelProperty(value = "商家备注")
	private String remark;
	
	/**
	 * 品牌下面的商品关联
	 */
	private List<WccGoodsInfoDto> goodsInfoList;
}
