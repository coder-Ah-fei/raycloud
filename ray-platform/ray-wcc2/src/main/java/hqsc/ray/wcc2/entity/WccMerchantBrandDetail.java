/*
 * Copyright 2020-2030, RayCloud, DAOTIANDI Technology Inc All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Author: pangu(7333791@qq.com)
 */
package hqsc.ray.wcc2.entity;

import hqsc.ray.wcc2.entity.base.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/**
 * 品牌方/商家详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccMerchantBrandDetail对象", description = "品牌方/商家详情表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_merchant_brand_detail")
@org.hibernate.annotations.Table(appliesTo = "wcc_merchant_brand_detail", comment = "品牌方/商家详情表")
public class WccMerchantBrandDetail extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "MERCHANT_BRAND_DETAIL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 品牌方/商家图标
	 */
	@ApiModelProperty(value = "品牌方/商家图标")
	@OneToOne
	@JoinColumn(name = "ICON", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private WccAttachment icon;
	/**
	 * 品牌方/商家名称
	 */
	@ApiModelProperty(value = "品牌方/商家名称")
	@Column(name = "NAME")
	private String name;
	/**
	 * 品牌方/商家联系方式
	 */
	@ApiModelProperty(value = "品牌方/商家联系方式")
	@Column(name = "PHONE")
	private String phone;
	/**
	 * 评分级别
	 */
	@ApiModelProperty(value = "评分级别")
	@Column(name = "LEVEL")
	private String level;
	/**
	 * 平台
	 */
	@ApiModelProperty(value = "平台")
	@Column(name = "PLATFORM")
	private String platform;
	/**
	 * 类目
	 */
	@ApiModelProperty(value = "类目")
	@Column(name = "TYPE")
	private String type;
	/**
	 * sku数量
	 */
	@ApiModelProperty(value = "sku数量")
	@Column(name = "SKU_COUNT")
	private Integer skuCount;
	/**
	 * 定位
	 */
	@ApiModelProperty(value = "定位")
	@Column(name = "ORIENT")
	private String orient;
	/**
	 * 合作渠道
	 */
	@ApiModelProperty(value = "合作渠道")
	@Column(name = "PARTNER_CHANNEL")
	private String partnerChannel;
	/**
	 * 产品优势
	 */
	@ApiModelProperty(value = "产品优势")
	@Column(name = "PRODUCT_SUPERIORITY")
	private String productSuperiority;
	/**
	 * 合作模式
	 */
	@ApiModelProperty(value = "合作模式")
	@Column(name = "PARTNER_MODEL")
	private String partnerModel;
	/**
	 * 独家优势
	 */
	@ApiModelProperty(value = "独家优势")
	@Column(name = "EXCLUSIVE_SUPERIORITY")
	private String exclusiveSuperiority;
	/**
	 * 推荐理由
	 */
	@ApiModelProperty(value = "推荐理由")
	@Column(name = "INTRODUCE")
	private String introduce;
	/**
	 * 商家备注
	 */
	@ApiModelProperty(value = "商家备注")
	@Column(name = "REMARK")
	private String remark;
	
	/**
	 * 品牌下面的商品关联
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "merchantBrand")
	private List<WccGoodsInfo> goodsInfoList;
}
