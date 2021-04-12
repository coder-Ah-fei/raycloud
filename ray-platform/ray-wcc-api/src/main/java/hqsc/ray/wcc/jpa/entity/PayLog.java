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
public class PayLog extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@Id
	@Column(name = "PAY_LOG_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	@ApiModelProperty(value = "订单")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "id")
	private Order order;
	
	@ApiModelProperty(value = "微信统一下单号")
	@Column(name = "ORDER_WECHAT_CODE", columnDefinition = "varchar(32) null comment '微信统一下单号'")
	private String orderWechatCode;
	
	
	@ApiModelProperty(value = "预支付交易会话标识")
	@Column(name = "PRE_PAY_ID", columnDefinition = "varchar(64) null comment '预支付交易会话标识'")
	private String prePayId;
	
	
	@ApiModelProperty(value = "支付金额")
	@Column(name = "PAY_PRICE", columnDefinition = "decimal(19,2) null comment '支付金额'")
	private BigDecimal payPrice;
	
	@ApiModelProperty(value = "支付人")
	@Column(name = "PAYER_OPENID", columnDefinition = "varchar(32) null comment '支付人'")
	private String payerOpenid;
	
	
	@ApiModelProperty(value = "支付方式")
	@Column(name = "PAY_MODE", columnDefinition = "varchar(255) null comment '支付方式'")
	@Enumerated(EnumType.STRING)
	private PayMode payMode;
	
	@ApiModelProperty(value = "支付状态（1成功 0支付中 -1失败）")
	@Column(name = "PAY_STATUS", columnDefinition = "char(1) default '1' comment '支付状态（1成功 0支付中 -1失败）'")
	private Integer payStatus = 0;
	
}
