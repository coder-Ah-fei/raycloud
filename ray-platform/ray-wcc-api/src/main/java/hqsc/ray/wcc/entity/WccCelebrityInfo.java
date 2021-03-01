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

import java.io.Serializable;
import java.math.BigDecimal;
import hqsc.ray.core.database.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 红人信息表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccCelebrityInfo对象", description = "红人信息表")
public class WccCelebrityInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "CELEBRITY_INFO_ID", type = IdType.AUTO)
	private Long celebrityInfoId;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@TableField(value = "BELONG_USERID")
	private Long belongUserId;
	/**
	* 昵称
	*/
	@ApiModelProperty(value = "昵称")
	@TableField("NICKNAME")
	private String nickname;
	/**
	* 头像
	*/
	@ApiModelProperty(value = "头像")
	@TableField("HEAD_PORTRAIT")
	private Long headPortrait;
	/**
	* 性别(0女1男)
	*/
	@ApiModelProperty(value = "性别(0女1男)")
	@TableField("GENDER")
	private Integer gender;
	/**
	* 带货领域
	*/
	@ApiModelProperty(value = "带货领域")
	@TableField("SCOPE")
	private String scope;
	/**
	* 所属平台
	*/
	@ApiModelProperty(value = "所属平台")
	@TableField("PLATFORM")
	private String platform;
	/**
	* 所属机构
	*/
	@ApiModelProperty(value = "所属机构")
	@TableField("ORGANIZATION_ID")
	private Long organizationId;
	/**
	* 现居住地
	*/
	@ApiModelProperty(value = "现居住地")
	@TableField("CURRENT_ADDRESS")
	private String currentAddress;

	/**
	 * 红人标签
	 */
	@ApiModelProperty(value = "红人标签")
	@TableField("TAG")
	private String tag;

	/**
	* 等级
	*/
	@ApiModelProperty(value = "等级")
	@TableField("LEVEL")
	private String level;
	/**
	* 年限
	*/
	@ApiModelProperty(value = "年限")
	@TableField("YEARS_LIMIT")
	private String yearsLimit;
	/**
	* 年龄
	*/
	@ApiModelProperty(value = "年龄")
	@TableField("AGE")
	private String age;
	/**
	* 公会
	*/
	@ApiModelProperty(value = "公会")
	@TableField("LABOR_UNION")
	private String laborUnion;
	/**
	* 简介
	*/
	@ApiModelProperty(value = "简介")
	@TableField("STATE")
	private String state;
	/**
	* 直播带货报价
	*/
	@ApiModelProperty(value = "直播带货报价")
	@TableField("LIVE_PRICE")
	private BigDecimal livePrice;
	/**
	* 短视频报价
	*/
	@ApiModelProperty(value = "短视频报价")
	@TableField("VIDEO")
	private BigDecimal video;

	/**
	 * 粉丝数量
	 */
	@ApiModelProperty(value = "粉丝数量")
	@TableField("FANS")
	private Integer fans;
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
