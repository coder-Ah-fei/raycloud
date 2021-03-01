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
package hqsc.ray.wcc.entity;

import hqsc.ray.core.database.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 品牌方/商家认证信息表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccBrandMerchantAuth对象", description = "品牌方/商家认证信息表")
public class WccBrandMerchantAuth implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "MERCHANT_BRAND_ID", type = IdType.AUTO)
	private Long merchantBrandId;
	/**
	* 品牌方/商家图标
	*/
	@ApiModelProperty(value = "品牌方/商家图标")
	@TableField("ICON")
	private Long icon;
	/**
	* 品牌方/商家名称
	*/
	@ApiModelProperty(value = "品牌方/商家名称")
	@TableField("NAME")
	private String name;
	/**
	* 入驻类型(0品牌方1商家)
	*/
	@ApiModelProperty(value = "入驻类型(0品牌方1商家)")
	@TableField("ENTER_TYPE")
	private Integer enterType;
	/**
	* 联系人姓名
	*/
	@ApiModelProperty(value = "联系人姓名")
	@TableField("CONTACT_NAME")
	private String contactName;
	/**
	* 联系人微信号
	*/
	@ApiModelProperty(value = "联系人微信号")
	@TableField("CONTACT_WECHAT_NUM")
	private Long contactWechatNum;
	/**
	* 联系人微信二维码
	*/
	@ApiModelProperty(value = "联系人微信二维码")
	@TableField("WECHAT_QRCODE_ICON")
	private Long wechatQrcodeIcon;
	/**
	* 入驻类型平台(0淘宝1天猫)
	*/
	@ApiModelProperty(value = "入驻类型平台(0淘宝1天猫)")
	@TableField("ENTER_PLATFORM_TYPE")
	private Integer enterPlatformType;
	/**
	* 店铺名称
	*/
	@ApiModelProperty(value = "店铺名称")
	@TableField("SHOP_NAME")
	private String shopName;
	/**
	* 店铺链接
	*/
	@ApiModelProperty(value = "店铺链接")
	@TableField("SHOP_LINK")
	private Long shopLink;
	/**
	* 资质文件
	*/
	@ApiModelProperty(value = "资质文件")
	@TableField("QUALIFIED_FILE")
	private Long qualifiedFile;
	/**
	* 经营范围
	*/
	@ApiModelProperty(value = "经营范围")
	@TableField("BUSINESS_SCOPE")
	private String businessScope;
	/**
	* 经营范围描述
	*/
	@ApiModelProperty(value = "经营范围描述")
	@TableField("BUSINESS_SCOPE_STATE")
	private String businessScopeState;
	/**
	* 状态（1正常 0禁用）
	*/
	@ApiModelProperty(value = "状态（1正常 0禁用）")
	@TableField("STATUS")
	private Integer status;
	/**
	* 1 删除  0正常
	*/
	@ApiModelProperty(value = "1 删除  0正常")
	@TableField("IS_DELETE")
	private Integer isDelete;
	/**
	* 创建人
	*/
	@ApiModelProperty(value = "创建人")
	@TableField("CREATED_BY")
	private String createdBy;
	/**
	* 创建人姓名
	*/
	@ApiModelProperty(value = "创建人姓名")
	@TableField("CREATED_BY_USER")
	private String createdByUser;
	/**
	* 创建时间
	*/
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATION_DATE")
	private LocalDateTime creationDate;
	/**
	* 修改人姓名
	*/
	@ApiModelProperty(value = "修改人姓名")
	@TableField("LAST_UPDATED_BY_USER")
	private String lastUpdatedByUser;
	/**
	* 最后更新人
	*/
	@ApiModelProperty(value = "最后更新人")
	@TableField("LAST_UPDATED_BY")
	private String lastUpdatedBy;
	/**
	* 最后更新时间
	*/
	@ApiModelProperty(value = "最后更新时间")
	@TableField("LAST_UPDATE_DATE")
	private LocalDateTime lastUpdateDate;


}
