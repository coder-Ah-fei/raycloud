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

/**
 * 考试表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccExam对象", description = "考试表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_exam")
@org.hibernate.annotations.Table(appliesTo = "wcc_exam", comment = "考试表")
public class WccExam extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "EXAM_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 考试科目
	 */
	@ApiModelProperty(value = "考试科目")
	@Column(name = "EXAM_SUBJECT")
	private String examSubject;
	/**
	 * 考试时间(分钟)
	 */
	@ApiModelProperty(value = "考试时间(分钟)")
	@Column(name = "EXAM_TIME")
	private Integer examTime;
	/**
	 * 考试方式(0选择题1简答题)
	 */
	@ApiModelProperty(value = "考试方式(0选择题1简答题)")
	@Column(name = "EXAM_WAY")
	private Integer examWay;
	/**
	 * 题目总数
	 */
	@ApiModelProperty(value = "题目总数")
	@Column(name = "TITLE_COUNT")
	private Integer titleCount;
	
	
}
