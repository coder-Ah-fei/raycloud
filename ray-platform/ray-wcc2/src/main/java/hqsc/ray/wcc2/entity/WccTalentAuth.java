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

import com.fasterxml.jackson.annotation.JsonIgnore;
import hqsc.ray.wcc2.entity.base.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 达人认证表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccTalentAuth对象", description = "达人认证表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_talent_auth")
@org.hibernate.annotations.Table(appliesTo = "wcc_talent_auth", comment = "达人认证表")
public class WccTalentAuth extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "TALENT_AUTH_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 关联用户
	 */
	@ApiModelProperty(value = "关联用户")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private WccUser belongUser;
	/**
	 * 微信账号
	 */
	@ApiModelProperty(value = "微信账号")
	@Column(name = "WECHAT_NUMBER")
	private String wechatNumber;
	/**
	 * 抖音认证(0已认证1未认证)
	 */
	@ApiModelProperty(value = "抖音认证(0已认证1未认证)")
	@Column(name = "TIKTOK_AUTH")
	private Integer tiktokAuth;
	/**
	 * 快手(0已认证1未认证)
	 */
	@ApiModelProperty(value = "快手(0已认证1未认证)")
	@Column(name = "KUAISHOU_AUTH")
	private Integer kuaishouAuth;
	/**
	 * B站认证(0已认证1未认证)2
	 */
	@ApiModelProperty(value = "B站认证(0已认证1未认证)2")
	@Column(name = "BILIBILI_AUTH")
	private Integer bilibiliAuth;
	/**
	 * 收货地址
	 */
	@ApiModelProperty(value = "收货地址")
	@Column(name = "RECEIVE_ADDRESS")
	private String receiveAddress;
	
	
}
