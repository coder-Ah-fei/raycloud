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

import javax.persistence.*;
import java.util.Date;

/**
 * 主播任务详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccTaskDetail对象", description = "主播任务详情表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_task_detail")
@org.hibernate.annotations.Table(appliesTo = "wcc_task_detail", comment = "主播任务详情表")
public class WccTaskDetail extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "TASK_DETAIL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	
	/**
	 * 任务标题
	 */
	@ApiModelProperty(value = "任务标题")
	@Column(name = "TASK_TITEL")
	private String taskTitel;
	/**
	 * 任务类型(0直播1短视频)
	 */
	@ApiModelProperty(value = "任务类型(0直播1短视频)")
	@Column(name = "TASK_TYPE")
	private Integer taskType;
	/**
	 * 任务发布时间
	 */
	@ApiModelProperty(value = "任务发布时间")
	@Column(name = "RELEASE_TIME")
	private Date releaseTime;
	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "公司名称")
	@Column(name = "COMPANY_NAME")
	private String companyName;
	/**
	 * 任务期限
	 */
	@ApiModelProperty(value = "任务期限")
	@Column(name = "TIME_LIMIT")
	private Date timeLimit;
	/**
	 * 内容主题
	 */
	@ApiModelProperty(value = "内容主题")
	@Column(name = "BODY_THYEME")
	private String bodyThyeme;
	/**
	 * 任务描述
	 */
	@ApiModelProperty(value = "任务描述")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "TASK_STATE")
	private String taskState;
	/**
	 * 工作地点
	 */
	@ApiModelProperty(value = "工作地点")
	@Column(name = "WORK_ADDRESS")
	private String workAddress;
	/**
	 * 工作日期
	 */
	@ApiModelProperty(value = "工作日期")
	@Column(name = "WORK_TIME")
	private Date workTime;
	/**
	 * 每天工作段(早)
	 */
	@ApiModelProperty(value = "每天工作段(早)")
	@Column(name = "MORNING")
	private Date morning;
	/**
	 * 每天工作段(晚)
	 */
	@ApiModelProperty(value = "每天工作段(晚)")
	@Column(name = "EVENING")
	private Date evening;
	
}
