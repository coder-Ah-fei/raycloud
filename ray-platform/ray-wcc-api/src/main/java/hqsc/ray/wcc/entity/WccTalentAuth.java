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
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 达人认证表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccTalentAuth对象", description = "达人认证表")
public class WccTalentAuth implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "TALENT_AUTH_ID", type = IdType.AUTO)
	private Long talentAuthId;
	/**
	* 关联用户
	*/
	@ApiModelProperty(value = "关联用户")
	@TableField("USER_ID")
	private Long userId;
	/**
	* 微信账号
	*/
	@ApiModelProperty(value = "微信账号")
	@TableField("WECHAT_NUMBER")
	private String wechatNumber;
	/**
	* 抖音认证(0已认证1未认证)
	*/
	@ApiModelProperty(value = "抖音认证(0已认证1未认证)")
	@TableField("TIKTOK_AUTH")
	private Integer tiktokAuth;
	/**
	* 快手(0已认证1未认证)
	*/
	@ApiModelProperty(value = "快手(0已认证1未认证)")
	@TableField("KUAISHOU_AUTH")
	private Integer kuaishouAuth;
	/**
	* B站认证(0已认证1未认证)2
	*/
	@ApiModelProperty(value = "B站认证(0已认证1未认证)2")
	@TableField("BILIBILI_AUTH")
	private Integer bilibiliAuth;
	/**
	* 收货地址
	*/
	@ApiModelProperty(value = "收货地址")
	@TableField("RECEIVE_ADDRESS")
	private String receiveAddress;
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
