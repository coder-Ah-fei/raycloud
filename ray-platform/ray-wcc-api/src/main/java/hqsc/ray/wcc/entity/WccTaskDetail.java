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
 * 主播任务详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccTaskDetail对象", description = "主播任务详情表")
public class WccTaskDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "TASK_DETAIL_ID", type = IdType.AUTO)
	private Long taskDetailId;
	/**
	* 任务标题
	*/
	@ApiModelProperty(value = "任务标题")
	@TableField("TASK_TITEL")
	private String taskTitel;
	/**
	* 任务类型(0直播1短视频)
	*/
	@ApiModelProperty(value = "任务类型(0直播1短视频)")
	@TableField("TASK_TYPE")
	private Integer taskType;
	/**
	* 任务发布时间
	*/
	@ApiModelProperty(value = "任务发布时间")
	@TableField("RELEASE_TIME")
	private LocalDateTime releaseTime;
	/**
	* 公司名称
	*/
	@ApiModelProperty(value = "公司名称")
	@TableField("COMPANY_NAME")
	private String companyName;
	/**
	* 任务期限
	*/
	@ApiModelProperty(value = "任务期限")
	@TableField("TIME_LIMIT")
	private LocalDateTime timeLimit;
	/**
	* 内容主题
	*/
	@ApiModelProperty(value = "内容主题")
	@TableField("BODY_THYEME")
	private String bodyThyeme;
	/**
	* 任务描述
	*/
	@ApiModelProperty(value = "任务描述")
	@TableField("TASK_STATE")
	private String taskState;
	/**
	* 工作地点
	*/
	@ApiModelProperty(value = "工作地点")
	@TableField("WORK_ADDRESS")
	private String workAddress;
	/**
	* 工作日期
	*/
	@ApiModelProperty(value = "工作日期")
	@TableField("WORK_TIME")
	private LocalDateTime workTime;
	/**
	* 每天工作段(早)
	*/
	@ApiModelProperty(value = "每天工作段(早)")
	@TableField("MORNING")
	private LocalDateTime morning;
	/**
	* 每天工作段(晚)
	*/
	@ApiModelProperty(value = "每天工作段(晚)")
	@TableField("EVENING")
	private LocalDateTime evening;
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
