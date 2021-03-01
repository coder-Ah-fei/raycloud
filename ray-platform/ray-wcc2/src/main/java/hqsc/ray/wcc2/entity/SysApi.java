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

import hqsc.ray.wcc2.entity.base.BasicSysEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 系统接口表实体类
 *
 * @author pangu
 * @since 2020-10-14
 */
@ApiModel(value = "SysApi对象", description = "系统接口表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_api")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_api", comment = "系统接口表")
public class SysApi extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	
	/**
	 * 接口编码
	 */
	@ApiModelProperty(value = "接口编码")
	private String code;
	/**
	 * 接口名称
	 */
	@ApiModelProperty(value = "接口名称")
	private String name;
	/**
	 * 接口描述
	 */
	@ApiModelProperty(value = "接口描述")
	private String notes;
	/**
	 * 请求方法
	 */
	@ApiModelProperty(value = "请求方法")
	private String method;
	/**
	 * 类名
	 */
	@ApiModelProperty(value = "类名")
	@Column(name = "class_name")
	private String className;
	/**
	 * 方法名
	 */
	@ApiModelProperty(value = "方法名")
	@Column(name = "method_name")
	private String methodName;
	/**
	 * 请求路径
	 */
	@ApiModelProperty(value = "请求路径")
	private String path;
	/**
	 * 响应类型
	 */
	@ApiModelProperty(value = "响应类型")
	@Column(name = "content_type")
	private String contentType;
	/**
	 * 服务ID
	 */
	@ApiModelProperty(value = "服务ID")
	@Column(name = "service_id")
	private String serviceId;
	/**
	 * API状态:0:禁用 1:启用
	 */
	@ApiModelProperty(value = "API状态:0:禁用 1:启用")
	private String status;
	/**
	 * 是否认证:0:不认证 1:认证
	 */
	@ApiModelProperty(value = "是否认证:0:不认证 1:认证")
	private String auth;
	/**
	 * 删除标识
	 */
	@ApiModelProperty(value = "删除标识")
	@Column(name = "is_deleted")
	private String isDeleted;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	@Column(name = "tenant_id")
	private Integer tenantId;
	
	
}
