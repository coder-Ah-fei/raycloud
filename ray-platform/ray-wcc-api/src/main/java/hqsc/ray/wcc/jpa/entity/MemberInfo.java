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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 订单表
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "MemberInfo对象", description = "会员信息表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_member_info")
@org.hibernate.annotations.Table(appliesTo = "wcc_member_info", comment = "会员信息")
public class MemberInfo extends BasicEntity {
	
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
	
	@ApiModelProperty(value = "用户")
	@OneToOne
	@JoinColumn(name = "USER_ID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaWccUser wccUser;
	
	@ApiModelProperty(value = "会员开始日期")
	@Column(name = "START_DATA_TIME", columnDefinition = "datetime null comment '订单结束时间'")
	private LocalDateTime startDataTime;
	
	@ApiModelProperty(value = "会员结束时间")
	@Column(name = "END_DATA_TIME", columnDefinition = "datetime null comment '订单结束时间'")
	private LocalDateTime endDataTime;
	
	
	@ApiModelProperty(value = "续费模式")
	@Column(name = "PAYMENT_MODE", columnDefinition = "varchar(255) null comment '续费模式'")
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
	@ApiModelProperty(value = "最后一次续费的订单")
	@OneToOne
	@JoinColumn(name = "ORDER_MEMBER_ID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private OrderMember orderMember;
	
}
