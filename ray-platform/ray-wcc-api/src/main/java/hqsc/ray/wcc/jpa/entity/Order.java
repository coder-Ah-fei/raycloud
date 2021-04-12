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
import hqsc.ray.wcc.jpa.common.enums.OrderStatus;
import hqsc.ray.wcc.jpa.common.enums.OrderType;
import hqsc.ray.wcc.jpa.common.enums.PayMode;
import hqsc.ray.wcc.jpa.entity.base.BasicEntity;
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
 * 订单表
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "Order对象", description = "订单表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_order")
@org.hibernate.annotations.Table(appliesTo = "wcc_order", comment = "订单表")
public class Order extends BasicEntity {
	
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
	
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private JpaWccUser jpaWccUser;
	
	@ApiModelProperty(value = "订单编号")
	@Column(name = "ORDER_CODE", columnDefinition = "varchar(255) NOT NULL COMMENT '订单编号'")
	private String orderCode;
	
	@ApiModelProperty(value = "订单类型")
	@Column(name = "ORDER_TYPE", columnDefinition = "varchar(255) null comment '订单类型'")
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	
	@ApiModelProperty(value = "订单结束时间")
	@Column(name = "FINISH_DATA_TIME", columnDefinition = "datetime null comment '订单结束时间'")
	private LocalDateTime finishDataTime;
	
	@ApiModelProperty(value = "支付时间")
	@Column(name = "PAY_DATA_TIME", columnDefinition = "datetime null comment '支付时间'")
	private LocalDateTime payDataTime;
	
	@ApiModelProperty(value = "是否支付")
	@Column(name = "IS_PAY", columnDefinition = "bit(1) NOT NULL DEFAULT false comment '是否支付' ")
	private Boolean pay;
	
	@ApiModelProperty(value = "支付方式")
	@Column(name = "PAY_MODE", columnDefinition = "varchar(255) null comment '支付方式'")
	@Enumerated(EnumType.STRING)
	private PayMode payMode;
	
	@ApiModelProperty(value = "支付金额（支付后更新）")
	@Column(name = "PAY_PRICE", columnDefinition = "decimal(19,2) null comment '支付金额（支付后更新）'")
	private BigDecimal payPrice;
	
	@ApiModelProperty(value = "订单合计金额")
	@Column(name = "ORDER_PRICE", columnDefinition = "decimal(19,2) null comment '订单合计金额'")
	private BigDecimal orderPrice;
	
	@ApiModelProperty(value = "订单状态")
	@Column(name = "ORDER_STATUS", columnDefinition = "varchar(255) null comment '订单状态'")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
	private OrderMember orderMember;
}
