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
 * 用户表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccUser对象", description = "用户表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_user")
@org.hibernate.annotations.Table(appliesTo = "wcc_user", comment = "用户表")
public class WccUser extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	
	
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@Column(name = "NAME")
	private String name;
	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	@Column(name = "NICKNAME")
	private String nickname;
	/**
	 * 用户手机号
	 */
	@ApiModelProperty(value = "用户手机号")
	@Column(name = "PHONE")
	private String phone;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	@Column(name = "PASSWORD")
	private String password;
	/**
	 * 盐
	 */
	@ApiModelProperty(value = "盐")
	@Column(name = "SALT")
	private String salt;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	@OneToOne
	@JoinColumn(name = "HEAD_PORTRAIT", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private WccAttachment headPortrait;
	
	
	/**
	 * 微信昵称
	 */
	@ApiModelProperty(value = "微信昵称")
	@Column(name = "WECHAT_NICKNAME")
	private String wechatNickname;
	/**
	 * 微信unionID
	 */
	@ApiModelProperty(value = "微信unionID")
	@Column(name = "WECHAT_UNION_ID")
	private String wechatUnionId;
	/**
	 * 微信头像地址
	 */
	@ApiModelProperty(value = "微信头像地址")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "WECHAT_HEAD_PORTRAIT_ADDRESS")
	private String wechatHeadPortraitAddress;
	/**
	 * 认证类型(0个人认证1企业认证)
	 */
	@ApiModelProperty(value = "认证类型(0个人认证1企业认证)")
	@Column(name = "AUTH_TYPE")
	private Integer authType;
	/**
	 * 会员（0非会员1会员）
	 */
	@ApiModelProperty(value = "会员（0非会员1会员）")
	@Column(name = "MEMBER")
	private Integer member;
	/**
	 * 会员等级
	 */
	@ApiModelProperty(value = "会员等级")
	@Column(name = "MEMBER_RANK")
	private Integer memberRank;
	
	/**
	 * 会员等级
	 */
	@ApiModelProperty(value = "红人标记")
	@Column(name = "CELEBRITY_FLAG")
	private Integer celebrityFlag;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	@Column(name = "GENDER")
	private Integer gender;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "年龄")
	@Column(name = "AGE")
	private Integer age;
	/**
	 * 用户等级
	 */
	@ApiModelProperty(value = "用户等级")
	@Column(name = "USER_RANK")
	private Integer userRank;
	/**
	 * 用户简介
	 */
	@ApiModelProperty(value = "用户简介")
	@Column(name = "USER_INTRODUCE")
	private String userIntroduce;
	/**
	 * 状态（1正常 0禁用）
	 */
	@ApiModelProperty(value = "状态（1正常 0禁用）")
	@Column(name = "STATUS")
	private Integer status;
	
	
}
