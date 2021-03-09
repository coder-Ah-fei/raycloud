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
package hqsc.ray.wcc.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hqsc.ray.wcc.jpa.entity.base.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品信息表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccGoodsInfo对象", description = "商品信息表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_goods_info")
@org.hibernate.annotations.Table(appliesTo = "wcc_goods_info", comment = "商品信息表")
public class JpaWccGoodsInfo extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "GOODS_INFO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 所属品牌方/商家
	 */
	@ApiModelProperty(value = "所属品牌方/商家")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_BRAND_ID", referencedColumnName = "MERCHANT_BRAND_DETAIL_ID")
	@JsonIgnore
	private JpaWccMerchantBrandDetail merchantBrand;
	/**
	 * 主推商品名称
	 */
	@ApiModelProperty(value = "主推商品名称")
	@Column(name = "MAIN_PRODUCT")
	private String mainProduct;
	/**
	 * 图片
	 */
	@ApiModelProperty(value = "图片")
	@OneToOne
	@JoinColumn(name = "IMG_ID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaSysAttachment img;
	/**
	 * 供货价
	 */
	@ApiModelProperty(value = "供货价")
	@Column(name = "SUPPLY_PRICE")
	private BigDecimal supplyPrice;
	/**
	 * 原价
	 */
	@ApiModelProperty(value = "原价")
	@Column(name = "ORIGINAL_PRICE")
	private BigDecimal originalPrice;
	/**
	 * 产品文件
	 */
	@ApiModelProperty(value = "产品文件")
	@OneToOne
	@JoinColumn(name = "PRODUCT_FILE")
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaSysAttachment productFile;
	/**
	 * 产品优势
	 */
	@ApiModelProperty(value = "产品优势")
	@Column(name = "PRODUCT_SUPERIORITY", columnDefinition = "varchar(500) null comment '产品优势'")
	private String productSuperiority;
}
