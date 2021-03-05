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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 红人信息表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccCelebrityInfo对象", description = "红人信息表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_celebrity_info")
@org.hibernate.annotations.Table(appliesTo = "wcc_celebrity_info", comment = "红人信息表")
public class JpaWccCelebrityInfo extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@Id
	@Column(name = "CELEBRITY_INFO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "用户id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BELONG_USERID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private JpaWccUser belongUser;
	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	@Column(name = "NICKNAME")
	private String nickname;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	@OneToOne
	@JoinColumn(name = "HEAD_PORTRAIT", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaSysAttachment headPortrait;
	/**
	 * 性别(0女1男)
	 */
	@ApiModelProperty(value = "性别(0女1男)")
	@Column(name = "GENDER")
	private Integer gender;
	/**
	 * 带货领域
	 */
	@ApiModelProperty(value = "带货领域")
	@Column(name = "SCOPE")
	private String scope;
	/**
	 * 所属平台
	 */
	@ApiModelProperty(value = "所属平台")
	@Column(name = "PLATFORM")
	private String platform;
	/**
	 * 所属机构
	 */
	@ApiModelProperty(value = "所属机构")
	@Column(name = "ORGANIZATION_ID")
	private Long organizationId;
	/**
	 * 现居住地
	 */
	@ApiModelProperty(value = "现居住地")
	@Column(name = "CURRENT_ADDRESS")
	private String currentAddress;
	
	/**
	 * 红人标签
	 */
	@ApiModelProperty(value = "红人标签")
	@Column(name = "TAG")
	private String tag;
	
	/**
	 * 等级
	 */
	@ApiModelProperty(value = "等级")
	@Column(name = "LEVEL")
	private String level;
	/**
	 * 年限
	 */
	@ApiModelProperty(value = "年限")
	@Column(name = "YEARS_LIMIT")
	private String yearsLimit;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "年龄")
	@Column(name = "AGE")
	private String age;
	/**
	 * 公会
	 */
	@ApiModelProperty(value = "公会")
	@Column(name = "LABOR_UNION")
	private String laborUnion;
	/**
	 * 简介
	 */
	@ApiModelProperty(value = "简介")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "STATE")
	private String state;
	/**
	 * 直播带货报价
	 */
	@ApiModelProperty(value = "直播带货报价")
	@Column(name = "LIVE_PRICE")
	private BigDecimal livePrice;
	/**
	 * 短视频报价
	 */
	@ApiModelProperty(value = "短视频报价")
	@Column(name = "VIDEO")
	private BigDecimal video;
	
	/**
	 * 粉丝数量
	 */
	@ApiModelProperty(value = "粉丝数量")
	@Column(name = "FANS")
	private Integer fans;
	
	
}
