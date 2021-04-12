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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 发布信息表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data
@ApiModel(value = "WccReleaseInfo对象", description = "发布信息表")
public class WccReleaseInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@TableId(value = "RELEASE_INFO_ID", type = IdType.AUTO)
	private Long releaseInfoId;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	@TableField("TITEL")
	private String titel;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	@TableField("CONTENT")
	private String content;
	/**
	 * 附件id
	 */
	@ApiModelProperty(value = "附件id")
	@TableField("ATTACHMENT_ID")
	private Long attachmentId;
	/**
	 * 发布类型(0提问1话题2文章3视频)
	 */
	@ApiModelProperty(value = "发布类型")
	@TableField("TYPE")
	private Long type;
	/**
	 * 所属用户
	 */
	@ApiModelProperty(value = "所属用户")
	@TableField("BELONG_USER_ID")
	private Long belongUserId;
	/**
	 * 所属圈子
	 */
	@ApiModelProperty(value = "所属圈子")
	@TableField("BELONG_CIRCLE_ID")
	private Long belongCircleId;
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
	 * 1 删除  0正常
	 */
	@ApiModelProperty(value = "0 待审批  1审批通过 2审批未通过（临时处理）")
	@TableField("APPROVE_STATUS")
	private Integer approveStatus;
	
	
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
