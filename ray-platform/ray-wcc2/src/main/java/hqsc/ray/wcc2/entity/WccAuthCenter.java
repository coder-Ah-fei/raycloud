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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 认证中心表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccAuthCenter对象", description = "认证中心表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_auth_center")
@org.hibernate.annotations.Table(appliesTo = "wcc_auth_center", comment = "认证中心表")
public class WccAuthCenter extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "AUTH_CENTER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private WccUser wccUser;
	
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@Column(name = "NAME")
	private String name;
	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	@Column(name = "IDENTITY_CARD")
	private String identityCard;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	/**
	 * 身份证正面照
	 */
	@ApiModelProperty(value = "身份证正面照")
	@OneToOne
	@JoinColumn(name = "FRONT", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private WccAttachment front;
	/**
	 * 身份证反面照
	 */
	@ApiModelProperty(value = "身份证反面照")
	@OneToOne
	@JoinColumn(name = "BACK", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private WccAttachment back;
}
