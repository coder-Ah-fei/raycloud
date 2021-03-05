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
 * 系统黑名单表实体类
 *
 * @author pangu
 * @since 2020-08-26
 */
@ApiModel(value = "SysBlacklist对象", description = "系统黑名单表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_blacklist")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_blacklist", comment = "系统黑名单表")
public class JpaSysBlacklist extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * IP地址
	 */
	@ApiModelProperty(value = "IP地址")
	private String ip;
	/**
	 * 请求地址
	 */
	@ApiModelProperty(value = "请求地址")
	@Column(name = "request_uri")
	private String requestUri;
	/**
	 * 请求方法
	 */
	@ApiModelProperty(value = "请求方法")
	@Column(name = "request_method")
	private String requestMethod;
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@Column(name = "start_time")
	private String startTime;
	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@Column(name = "end_time")
	private String endTime;
	/**
	 * 状态：0:关闭 1:开启
	 */
	@ApiModelProperty(value = "状态：0:关闭 1:开启")
	private String status;
	
}
