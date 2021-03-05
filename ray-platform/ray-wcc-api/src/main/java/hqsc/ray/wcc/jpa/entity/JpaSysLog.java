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
import java.time.LocalDateTime;

/**
 * 系统日志表实体类
 *
 * @author pangu
 * @since 2020-07-15
 */
@ApiModel(value = "SysLog对象", description = "系统日志表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_log")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_log", comment = "系统日志表")
public class JpaSysLog extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 日志类型
	 */
	@ApiModelProperty(value = "日志类型")
	private String type;
	/**
	 * 跟踪ID
	 */
	@ApiModelProperty(value = "跟踪ID")
	@Column(name = "trace_id")
	private String traceId;
	/**
	 * 日志标题
	 */
	@ApiModelProperty(value = "日志标题")
	private String title;
	/**
	 * 操作内容
	 */
	@ApiModelProperty(value = "操作内容")
	private String operation;
	/**
	 * 执行方法
	 */
	@ApiModelProperty(value = "执行方法")
	private String method;
	
	/**
	 * 请求路径
	 */
	@ApiModelProperty(value = "请求路径")
	private String url;
	/**
	 * 参数
	 */
	@ApiModelProperty(value = "参数")
	private String params;
	/**
	 * ip地址
	 */
	@ApiModelProperty(value = "ip地址")
	private String ip;
	/**
	 * 耗时
	 */
	@ApiModelProperty(value = "耗时")
	@Column(name = "execute_time")
	private Long executeTime;
	/**
	 * 地区
	 */
	@ApiModelProperty(value = "地区")
	private String location;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	@Column(name = "create_by")
	private String createBy;
	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	@Column(name = "update_by")
	private String updateBy;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time")
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@Column(name = "update_time")
	private LocalDateTime updateTime;
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
	/**
	 * 异常信息
	 */
	@ApiModelProperty(value = "异常信息")
	private String exception;
	
	
}
