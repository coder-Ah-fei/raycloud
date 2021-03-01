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

import com.fasterxml.jackson.annotation.JsonIgnore;
import hqsc.ray.wcc2.entity.base.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户支付记录表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccPayLog对象", description = "用户支付记录表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_pay_log")
@org.hibernate.annotations.Table(appliesTo = "wcc_pay_log", comment = "用户支付记录表")
public class WccPayLog extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@Id
	@Column(name = "PAY_LOG_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "订单号")
	@Column(name = "ORDER_ID")
	private Long orderId;
	/**
	 * 第三方订单id
	 */
	@ApiModelProperty(value = "第三方订单id")
	@Column(name = "THIRDPARTY_ID")
	private Long thirdpartyId;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private WccUser wccUser;
	/**
	 * 支付类型(0课程1会员)
	 */
	@ApiModelProperty(value = "支付类型(0课程1会员)")
	@Column(name = "PAY_TYPE")
	private Integer payType;
	/**
	 * 所属id
	 */
	@ApiModelProperty(value = "所属id")
	@Column(name = "BELONG_ID")
	private Long belongId;
	/**
	 * 商品原价
	 */
	@ApiModelProperty(value = "商品原价")
	@Column(name = "PROTO_PRICE")
	private BigDecimal protoPrice;
	/**
	 * 支付金额
	 */
	@ApiModelProperty(value = "支付金额")
	@Column(name = "PAY_AMOUNT")
	private BigDecimal payAmount;
	/**
	 * 支付时间
	 */
	@ApiModelProperty(value = "支付时间")
	@Column(name = "PAY_TIME")
	private LocalDateTime payTime;
	
}
