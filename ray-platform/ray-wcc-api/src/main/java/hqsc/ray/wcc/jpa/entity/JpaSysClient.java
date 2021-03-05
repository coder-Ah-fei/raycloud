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
 * 客户端表实体类
 *
 * @author pangu
 * @since 2020-07-14
 */
@ApiModel(value = "SysClient对象", description = "客户端表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_client")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_client", comment = "客户端表")
public class JpaSysClient extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 客户端id
	 */
	@ApiModelProperty(value = "客户端标识")
	@Column(name = "client_id")
	private String clientId;
	/**
	 * 客户端密钥
	 */
	@ApiModelProperty(value = "客户端密钥")
	@Column(name = "client_secret")
	private String clientSecret;
	/**
	 * 资源集合
	 */
	@ApiModelProperty(value = "资源集合")
	@Column(name = "resource_ids")
	private String resourceIds;
	/**
	 * 授权范围
	 */
	@ApiModelProperty(value = "授权范围")
	private String scope;
	/**
	 * 授权类型
	 */
	@ApiModelProperty(value = "授权类型")
	@Column(name = "authorized_grant_types")
	private String authorizedGrantTypes;
	/**
	 * 回调地址
	 */
	@ApiModelProperty(value = "回调地址")
	@Column(name = "web_server_redirect_uri")
	private String webServerRedirectUri;
	/**
	 * 权限
	 */
	@ApiModelProperty(value = "权限")
	private String authorities;
	/**
	 * 令牌过期秒数
	 */
	@ApiModelProperty(value = "令牌过期秒数")
	@Column(name = "access_token_validity")
	private Integer accessTokenValidity;
	/**
	 * 刷新令牌过期秒数
	 */
	@ApiModelProperty(value = "刷新令牌过期秒数")
	@Column(name = "refresh_token_validity")
	private Integer refreshTokenValidity;
	/**
	 * 附加说明
	 */
	@ApiModelProperty(value = "附加说明")
	@Column(name = "additional_information")
	private String additionalInformation;
	/**
	 * 自动授权
	 */
	@ApiModelProperty(value = "自动授权")
	private String autoapprove;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private String status;
	/**
	 * 是否已删除
	 */
	@ApiModelProperty(value = "是否已删除")
	@Column(name = "is_deleted")
	private Integer isDeleted;
	
	
}
