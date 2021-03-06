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

import hqsc.ray.wcc.jpa.entity.base.BasicSysEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 系统路由表实体类
 *
 * @author pangu
 * @since 2020-10-17
 */
@ApiModel(value = "SysRoute对象", description = "系统路由表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_route")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_route", comment = "系统路由表")
public class JpaSysRoute extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 接口名称
	 */
	@ApiModelProperty(value = "接口名称")
	private String name;
	/**
	 * 路径前缀
	 */
	@ApiModelProperty(value = "路径前缀")
	private String path;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	private String url;
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
