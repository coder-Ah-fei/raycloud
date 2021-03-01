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
 * 品牌方/商家详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccMerchantBrandDetail对象", description = "品牌方/商家详情表")
public class WccMerchantBrandDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "MERCHANT_BRAND_DETAIL_ID", type = IdType.AUTO)
	private Long merchantBrandDetailId;
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
	 * 品牌方/商家名称
	 */
	@ApiModelProperty(value = "品牌方/商家联系方式")
	@TableField("PHONE")
	private String phone;
	/**
	* 评分级别
	*/
	@ApiModelProperty(value = "评分级别")
	@TableField("LEVEL")
	private String level;
	/**
	 * 平台
	 */
	@ApiModelProperty(value = "平台")
	@TableField("PLATFORM")
	private String platform;
	/**
	* 类目
	*/
	@ApiModelProperty(value = "类目")
	@TableField("TYPE")
	private String type;
	/**
	* sku数量
	*/
	@ApiModelProperty(value = "sku数量")
	@TableField("SKU_COUNT")
	private Integer skuCount;
	/**
	* 定位
	*/
	@ApiModelProperty(value = "定位")
	@TableField("ORIENT")
	private String orient;
	/**
	* 合作渠道
	*/
	@ApiModelProperty(value = "合作渠道")
	@TableField("PARTNER_CHANNEL")
	private String partnerChannel;
	/**
	* 产品优势
	*/
	@ApiModelProperty(value = "产品优势")
	@TableField("PRODUCT_SUPERIORITY")
	private String productSuperiority;
	/**
	* 合作模式
	*/
	@ApiModelProperty(value = "合作模式")
	@TableField("PARTNER_MODEL")
	private String partnerModel;
	/**
	* 独家优势
	*/
	@ApiModelProperty(value = "独家优势")
	@TableField("EXCLUSIVE_SUPERIORITY")
	private String exclusiveSuperiority;
	/**
	* 推荐理由
	*/
	@ApiModelProperty(value = "推荐理由")
	@TableField("INTRODUCE")
	private String introduce;
	/**
	 * 推荐理由
	 */
	@ApiModelProperty(value = "商家备注")
	@TableField("REMARK")
	private String remark;
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
