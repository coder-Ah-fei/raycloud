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
package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.common.enums.OrderStatus;
import hqsc.ray.wcc.jpa.common.enums.OrderType;
import hqsc.ray.wcc.jpa.common.enums.PayMode;
import hqsc.ray.wcc.jpa.form.basic.BaseForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
public class OrderForm extends BaseForm {
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long id;
	private Long orderId;
	
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long jpaWccUserId;
	
	@ApiModelProperty(value = "订单编号")
	private String orderCode;
	
	@ApiModelProperty(value = "订单类型")
	private OrderType orderType;
	
	@ApiModelProperty(value = "订单结束时间")
	private LocalDateTime finishDataTime;
	
	@ApiModelProperty(value = "支付时间")
	private LocalDateTime payDataTime;
	
	@ApiModelProperty(value = "是否支付")
	private Boolean pay;
	
	@ApiModelProperty(value = "支付方式")
	private PayMode payMode;
	
	@ApiModelProperty(value = "支付金额")
	private BigDecimal payPrice;
	
	@ApiModelProperty(value = "订单合计金额")
	private BigDecimal orderPrice;
	
	@ApiModelProperty(value = "订单状态")
	private OrderStatus orderStatus;
	
	private Long orderMemberId;
	
	private Long openVipConfigurationId;
	
	private Long courseId;
}
