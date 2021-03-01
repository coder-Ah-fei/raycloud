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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 开通会员价目表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccOpenVipPrice对象", description = "开通会员价目表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_open_vip_price")
@org.hibernate.annotations.Table(appliesTo = "wcc_open_vip_price", comment = "开通会员价目表")
public class WccOpenVipPrice extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "VIP_PRICE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long GOODS_INFO_ID;
	/**
	 * 包月现价
	 */
	@ApiModelProperty(value = "包月现价")
	@Column(name = "MONTH_CURRENT_PRICE")
	private BigDecimal monthCurrentPrice;
	/**
	 * 包月原价
	 */
	@ApiModelProperty(value = "包月原价")
	@Column(name = "MONTH_ORIGINA_PRICE")
	private BigDecimal monthOriginaPrice;
	/**
	 * 包月天数
	 */
	@ApiModelProperty(value = "包月天数")
	@Column(name = "MONTH_DAY_COUNT")
	private Integer monthDayCount;
	/**
	 * 下次包月续费价格
	 */
	@ApiModelProperty(value = "下次包月续费价格")
	@Column(name = "NEXT_MONTH_PRICE")
	private BigDecimal nextMonthPrice;
	/**
	 * 包季现价
	 */
	@ApiModelProperty(value = "包季现价")
	@Column(name = "QUARTER_CURRENT_PRICE")
	private BigDecimal quarterCurrentPrice;
	/**
	 * 包季原价
	 */
	@ApiModelProperty(value = "包季原价")
	@Column(name = "QUARTER_ORIGINA_PRICE")
	private BigDecimal quarterOriginaPrice;
	/**
	 * 包季天数
	 */
	@ApiModelProperty(value = "包季天数")
	@Column(name = "QUARTER_DAY_COUNT")
	private Integer quarterDayCount;
	/**
	 * 下次包季续费价格
	 */
	@ApiModelProperty(value = "下次包季续费价格")
	@Column(name = "NEXT_QUARTER_PRICE")
	private BigDecimal nextQuarterPrice;
	/**
	 * 包年现价
	 */
	@ApiModelProperty(value = "包年现价")
	@Column(name = "YEAR_CURRENT_PRICE")
	private BigDecimal yearCurrentPrice;
	/**
	 * 包年原价
	 */
	@ApiModelProperty(value = "包年原价")
	@Column(name = "YEAR_ORIGINA_PRICE")
	private BigDecimal yearOriginaPrice;
	/**
	 * 包年天数
	 */
	@ApiModelProperty(value = "包年天数")
	@Column(name = "YEAR_DAY_COUNT")
	private Integer yearDayCount;
	/**
	 * 下次包年续费价格
	 */
	@ApiModelProperty(value = "下次包年续费价格")
	@Column(name = "NEXT_YEAR_PRICE")
	private BigDecimal nextYearPrice;
	
}
