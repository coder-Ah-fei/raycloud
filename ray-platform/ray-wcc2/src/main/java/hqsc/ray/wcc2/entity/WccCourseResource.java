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

import javax.persistence.*;

/**
 * 课程附件表
 *
 * @author yang
 * @date 2021年2月28日
 */
@ApiModel(value = "WccCourseResource对象", description = "课程附件表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_course_resource")
@org.hibernate.annotations.Table(appliesTo = "wcc_course_resource", comment = "课程附件表")
public class WccCourseResource extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	
	@ApiModelProperty(value = "课程id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", referencedColumnName = "COURSE_ID")
	@JsonIgnore
	private WccCourse wccCourse;
	
	
	@ApiModelProperty(value = "附件id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTACHMENT_ID", referencedColumnName = "ATTACHMENT_ID")
	@JsonIgnore
	private WccAttachment wccAttachment;
	
	
	@ApiModelProperty(value = "附件类型（1、方形小图 2、详情页banner图 3、课程附件）")
	@Column(name = "RESOURCE_TYPE")
	private Integer resourceType;
	
	@ApiModelProperty(value = "排序（主要用于banner图，课程附件）")
	@Column(name = "SORT")
	private Integer sort;
	
}
