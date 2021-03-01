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
package hqsc.ray.wcc.entity;


import hqsc.ray.core.database.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccUser对象", description = "用户表")
public class WccUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "USER_ID", type = IdType.AUTO)
	private Long userId;
	/**
	* 姓名
	*/
	@ApiModelProperty(value = "姓名")
	@TableField("NAME")
	private String name;
	/**
	* 昵称
	*/
	@ApiModelProperty(value = "昵称")
	@TableField("NICKNAME")
	private String nickname;
	/**
	* 用户手机号
	*/
	@ApiModelProperty(value = "用户手机号")
	@TableField("PHONE")
	private String phone;
	/**
	* 密码
	*/
	@ApiModelProperty(value = "密码")
	@TableField("PASSWORD")
	private String password;
	/**
	* 盐
	*/
	@ApiModelProperty(value = "盐")
	@TableField("SALT")
	private String salt;
	/**
	* 头像
	*/
	@ApiModelProperty(value = "头像")
	@TableField("HEAD_PORTRAIT")
	private Long headPortrait;
	/**
	* 微信昵称
	*/
	@ApiModelProperty(value = "微信昵称")
	@TableField("WECHAT_NICKNAME")
	private String wechatNickname;
	/**
	* 微信unionID
	*/
	@ApiModelProperty(value = "微信unionID")
	@TableField("WECHAT_UNION_ID")
	private String wechatUnionId;
	/**
	* 微信头像地址
	*/
	@ApiModelProperty(value = "微信头像地址")
	@TableField("WECHAT_HEAD_PORTRAIT_ADDRESS")
	private String wechatHeadPortraitAddress;
	/**
	* 认证类型(0个人认证1企业认证)
	*/
	@ApiModelProperty(value = "认证类型(0个人认证1企业认证)")
	@TableField("AUTH_TYPE")
	private Integer authType;
	/**
	* 会员（0非会员1会员）
	*/
	@ApiModelProperty(value = "会员（0非会员1会员）")
	@TableField("MEMBER")
	private Integer member;
	/**
	* 会员等级
	*/
	@ApiModelProperty(value = "会员等级")
	@TableField("MEMBER_RANK")
	private Integer memberRank;

	/**
	 * 会员等级
	 */
	@ApiModelProperty(value = "红人标记")
	@TableField("CELEBRITY_FLAG")
	private Integer celebrityFlag;
	/**
	* 性别
	*/
	@ApiModelProperty(value = "性别")
	@TableField("GENDER")
	private Integer gender;
	/**
	* 年龄
	*/
	@ApiModelProperty(value = "年龄")
	@TableField("AGE")
	private Integer age;
	/**
	* 用户等级
	*/
	@ApiModelProperty(value = "用户等级")
	@TableField("USER_RANK")
	private Integer userRank;
	/**
	* 用户简介
	*/
	@ApiModelProperty(value = "用户简介")
	@TableField("USER_INTRODUCE")
	private String userIntroduce;
	/**
	* 状态（1正常 0禁用）
	*/
	@ApiModelProperty(value = "状态（1正常 0禁用）")
	@TableField("STATUS")
	private Integer status;
	/**
	* 1 删除  0正常
	*/
	@ApiModelProperty(value = "1 删除  0正常")
	@TableField("IS_DELETE")
	private Integer isDelete;
	/**
	* 创建人
	*/
	@ApiModelProperty(value = "创建人")
	@TableField("CREATED_BY")
	private String createdBy;
	/**
	* 创建人姓名
	*/
	@ApiModelProperty(value = "创建人姓名")
	@TableField("CREATED_BY_USER")
	private String createdByUser;
	/**
	* 创建时间
	*/
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATION_DATE")
	private LocalDateTime creationDate;
	/**
	* 修改人姓名
	*/
	@ApiModelProperty(value = "修改人姓名")
	@TableField("LAST_UPDATED_BY_USER")
	private String lastUpdatedByUser;
	/**
	* 最后更新人
	*/
	@ApiModelProperty(value = "最后更新人")
	@TableField("LAST_UPDATED_BY")
	private String lastUpdatedBy;
	/**
	* 最后更新时间
	*/
	@ApiModelProperty(value = "最后更新时间")
	@TableField("LAST_UPDATE_DATE")
	private LocalDateTime lastUpdateDate;


}
