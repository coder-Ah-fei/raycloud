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

import java.io.Serializable;
import java.math.BigDecimal;
import hqsc.ray.core.database.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 开通会员价目表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccOpenVipPrice对象", description = "开通会员价目表")
public class WccOpenVipPrice implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "VIP_PRICE_ID", type = IdType.AUTO)
	private Long vipPriceId;
	/**
	* 包月现价
	*/
	@ApiModelProperty(value = "包月现价")
	@TableField("MONTH_CURRENT_PRICE")
	private BigDecimal monthCurrentPrice;
	/**
	* 包月原价
	*/
	@ApiModelProperty(value = "包月原价")
	@TableField("MONTH_ORIGINA_PRICE")
	private BigDecimal monthOriginaPrice;
	/**
	* 包月天数
	*/
	@ApiModelProperty(value = "包月天数")
	@TableField("MONTH_DAY_COUNT")
	private Integer monthDayCount;
	/**
	* 下次包月续费价格
	*/
	@ApiModelProperty(value = "下次包月续费价格")
	@TableField("NEXT_MONTH_PRICE")
	private BigDecimal nextMonthPrice;
	/**
	* 包季现价
	*/
	@ApiModelProperty(value = "包季现价")
	@TableField("QUARTER_CURRENT_PRICE")
	private BigDecimal quarterCurrentPrice;
	/**
	* 包季原价
	*/
	@ApiModelProperty(value = "包季原价")
	@TableField("QUARTER_ORIGINA_PRICE")
	private BigDecimal quarterOriginaPrice;
	/**
	* 包季天数
	*/
	@ApiModelProperty(value = "包季天数")
	@TableField("QUARTER_DAY_COUNT")
	private Integer quarterDayCount;
	/**
	* 下次包季续费价格
	*/
	@ApiModelProperty(value = "下次包季续费价格")
	@TableField("NEXT_QUARTER_PRICE")
	private BigDecimal nextQuarterPrice;
	/**
	* 包年现价
	*/
	@ApiModelProperty(value = "包年现价")
	@TableField("YEAR_CURRENT_PRICE")
	private BigDecimal yearCurrentPrice;
	/**
	* 包年原价
	*/
	@ApiModelProperty(value = "包年原价")
	@TableField("YEAR_ORIGINA_PRICE")
	private BigDecimal yearOriginaPrice;
	/**
	* 包年天数
	*/
	@ApiModelProperty(value = "包年天数")
	@TableField("YEAR_DAY_COUNT")
	private Integer yearDayCount;
	/**
	* 下次包年续费价格
	*/
	@ApiModelProperty(value = "下次包年续费价格")
	@TableField("NEXT_YEAR_PRICE")
	private BigDecimal nextYearPrice;
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
