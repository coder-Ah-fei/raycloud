///*
// * Copyright 2020-2030, RayCloud, DAOTIANDI Technology Inc All rights reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// * Author: pangu(7333791@qq.com)
// */
//package hqsc.ray.wcc.jpa.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import hqsc.ray.wcc.jpa.entity.base.BasicEntity;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.experimental.Accessors;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//
///**
// * 订单表
// *
// * @author pangu
// * @since 2020-12-30
// */
//@ApiModel(value = "Order对象", description = "订单表")
//@Getter
//@Setter
//@Accessors(chain = true)
//@Entity
//@Table(name = "wcc_order")
//@org.hibernate.annotations.Table(appliesTo = "wcc_order", comment = "订单表")
//public class Order extends BasicEntity {
//
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * 主键id
//	 */
//	@ApiModelProperty(value = "主键id")
//	@Id
//	@Column(name = "ID")
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
//	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
//	private Long id;
//
//	/**
//	 * 用户id
//	 */
//	@ApiModelProperty(value = "用户id")
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
//	@JsonIgnore
//	private JpaWccUser jpaWccUser;
//	/**
//	 * 资源id
//	 */
//	@ApiModelProperty(value = "资源id")
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "ATTACHMENT_ID", referencedColumnName = "id")
//	@JsonIgnore
//	private JpaSysAttachment sysAttachment;
//
//
//	/**
//	 * 是否有权限访问 0.否|1.是
//	 */
//	@ApiModelProperty(value = "是否有权限访问 0.否|1.是")
//	@Column(name = "CAN_ACCESS")
//	private Boolean canAccess;
//}
