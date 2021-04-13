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
import java.math.BigDecimal;

/**
 * 课程订单表
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "OrderCourse对象", description = "课程订单表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_order_course")
@org.hibernate.annotations.Table(appliesTo = "wcc_order_course", comment = "课程订单表")
public class OrderCourse extends BasicEntity {
	
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
	
	
	@ApiModelProperty(value = "订单")
	@OneToOne
	@JoinColumn(name = "ORDER_ID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Order order;
	
	
	@ApiModelProperty(value = "课程id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", referencedColumnName = "COURSE_ID")
	@JsonIgnore
	private JpaWccCourse jpaWccCourse;
	/**
	 * 课程标题
	 */
	@ApiModelProperty(value = "课程标题")
	@Column(name = "COURSE_TITEL")
	private String courseTitel;
	
	/**
	 * 课程类型(0系统课1进阶课)
	 */
	@ApiModelProperty(value = "课程类型(0系统课1进阶课)")
	@Column(name = "COURSE_TYPE")
	private Integer courseType;
	/**
	 * 课程现价
	 */
	@ApiModelProperty(value = "课程现价")
	@Column(name = "CURRENT_PRICE")
	private BigDecimal currentPrice;
	/**
	 * 课程原价
	 */
	@ApiModelProperty(value = "课程原价")
	@Column(name = "ORIGINAL_PRICE")
	private BigDecimal originalPrice;
	/**
	 * 会员是否免费(0免费1收费)
	 */
	@ApiModelProperty(value = "会员是否免费(0免费1收费)")
	@Column(name = "VIP_IS_FREE")
	private Integer vipIsFree;
	/**
	 * 会员收费价格
	 */
	@ApiModelProperty(value = "会员收费价格")
	@Column(name = "VIP_PRICE")
	private BigDecimal vipPrice;
	/**
	 * 销售数量
	 */
	@ApiModelProperty(value = "销售数量")
	@Column(name = "SELL_COUNT")
	private Integer sellCount;
	/**
	 * 课程标题介绍
	 */
	@ApiModelProperty(value = "课程标题介绍")
	@Column(name = "COURSE_TITEL_INTRODUCE")
	private String courseTitelIntroduce;
	/**
	 * 课程内容介绍
	 */
	@ApiModelProperty(value = "课程内容介绍")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "COURSE_BODY_INTRODUCE")
	private String courseBodyIntroduce;
	/**
	 * 连载状态(0已完结1连载中)
	 */
	@ApiModelProperty(value = "连载状态(0已完结1连载中)")
	@Column(name = "IS_SERIAL")
	private Integer isSerial;
	
	@ApiModelProperty(value = "是否热门课程(1是 0否)")
	@Column(name = "IS_HOT")
	private Integer isHot;
	
	@ApiModelProperty(value = "是否推荐课程(1是 0否)")
	@Column(name = "IS_RECOMMEND")
	private Integer isRecommend;
	
}
