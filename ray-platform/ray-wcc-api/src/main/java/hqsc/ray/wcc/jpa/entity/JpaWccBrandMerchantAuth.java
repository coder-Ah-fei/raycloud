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

/**
 * 品牌方/商家认证信息表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccBrandMerchantAuth对象", description = "品牌方/商家认证信息表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_brand_merchant_auth")
@org.hibernate.annotations.Table(appliesTo = "wcc_brand_merchant_auth", comment = "品牌方/商家认证信息表")
public class JpaWccBrandMerchantAuth extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@Id
	@Column(name = "MERCHANT_BRAND_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	/**
	 * 品牌方/商家图标
	 */
	@ApiModelProperty(value = "品牌方/商家图标")
	@OneToOne
	@JoinColumn(name = "ICON", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaSysAttachment icon;
	
	/**
	 * 品牌方/商家名称
	 */
	@ApiModelProperty(value = "品牌方/商家名称")
	@Column(name = "NAME")
	private String name;
	/**
	 * 入驻类型(0品牌方1商家)
	 */
	@ApiModelProperty(value = "入驻类型(0品牌方1商家)")
	@Column(name = "ENTER_TYPE")
	private Integer enterType;
	/**
	 * 联系人姓名
	 */
	@ApiModelProperty(value = "联系人姓名")
	@Column(name = "CONTACT_NAME")
	private String contactName;
	/**
	 * 联系人微信号
	 */
	@ApiModelProperty(value = "联系人微信号")
	@Column(name = "CONTACT_WECHAT_NUM")
	private String contactWechatNum;
	/**
	 * 联系人微信二维码
	 */
	@ApiModelProperty(value = "联系人微信二维码/商家图标")
	@OneToOne
	@JoinColumn(name = "WECHAT_QRCODE_ICON", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaSysAttachment wechatQrcodeIcon;
	/**
	 * 入驻类型平台(0淘宝1天猫)
	 */
	@ApiModelProperty(value = "入驻类型平台(0淘宝1天猫)")
	@Column(name = "ENTER_PLATFORM_TYPE")
	private Integer enterPlatformType;
	/**
	 * 店铺名称
	 */
	@ApiModelProperty(value = "店铺名称")
	@Column(name = "SHOP_NAME")
	private String shopName;
	/**
	 * 店铺链接
	 */
	@ApiModelProperty(value = "店铺链接")
	@Column(name = "SHOP_LINK")
	private String shopLink;
	/**
	 * 资质文件
	 */
	@ApiModelProperty(value = "资质文件")
	@OneToOne
	@JoinColumn(name = "QUALIFIED_FILE", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaSysAttachment qualifiedFile;
	/**
	 * 经营范围
	 */
	@ApiModelProperty(value = "经营范围")
	@Column(name = "BUSINESS_SCOPE")
	private String businessScope;
	/**
	 * 经营范围描述
	 */
	@ApiModelProperty(value = "经营范围描述")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "BUSINESS_SCOPE_STATE")
	private String businessScopeState;
	
	
}
