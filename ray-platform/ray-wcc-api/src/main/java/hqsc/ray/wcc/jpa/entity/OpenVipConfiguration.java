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

import hqsc.ray.wcc.jpa.common.enums.PaymentMode;
import hqsc.ray.wcc.jpa.entity.base.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 开通会员配置表
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "OpenVipConfiguration对象", description = "开通会员配置表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_open_vip_configuration")
@org.hibernate.annotations.Table(appliesTo = "wcc_open_vip_configuration", comment = "开通会员配置表")
public class OpenVipConfiguration extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	@ApiModelProperty(value = "配置名称")
	@Column(name = "SETTING_NAME", columnDefinition = "varchar(100) null comment '配置名称'")
	private String settingName;
	
	@ApiModelProperty(value = "续费模式")
	@Column(name = "PAYMENT_MODE", columnDefinition = "varchar(255) null comment '续费模式'")
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
	@ApiModelProperty(value = "原价")
	@Column(name = "ORIGINAL_PRICE", columnDefinition = "decimal(19,2) null comment '原价'")
	private BigDecimal originalPrice;
	
	@ApiModelProperty(value = "现价")
	@Column(name = "PRESENT_PRICE", columnDefinition = "decimal(19,2) null comment '现价'")
	private BigDecimal presentPrice;
	
	@ApiModelProperty(value = "下次续费的价格")
	@Column(name = "NEXT_PRICE", columnDefinition = "decimal(19,2) null comment '下次续费的价格'")
	private BigDecimal nextPrice;
	
	@ApiModelProperty(value = "天数")
	@Column(name = "DAY_NUM", columnDefinition = "int(11) NOT NULL DEFAULT '0' comment '天数'")
	private int dayNum;
	
	@ApiModelProperty(value = "会员有效期的计算方式（0，按自然月；1，按天数）")
	@Column(name = "VALIDITY_TYPE", columnDefinition = "int(1) NOT NULL DEFAULT '0' comment '会员有效期的计算方式（0，自然月；1，按天数）'")
	private int validityType;
	
	
	@ApiModelProperty(value = "排序")
	@Column(name = "SORT", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT '排序'")
	private int sort;
	
	
}
