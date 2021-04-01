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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 课程附件表
 *
 * @author yang
 * @date 2021年2月28日
 */
@ApiModel(value = "CourseChapter", description = "课程章节表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_course_chapter")
@org.hibernate.annotations.Table(appliesTo = "wcc_course_chapter", comment = "课程章节表")
public class JpaWccCourseChapter extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	@ApiModelProperty(value = "课程id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID", referencedColumnName = "COURSE_ID")
	@JsonIgnore
	private JpaWccCourse jpaWccCourse;
	
	@ApiModelProperty(value = "章节名称")
	@Column(name = "CHAPTER_TITLE")
	private String chapterTitle;
	
	
	@ApiModelProperty(value = "对应的附件资源，视频附件")
	@OneToOne
	@JoinColumn(name = "ATTACHMENT_ID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaSysAttachment attachment;
	
	/**
	 * 图文内容
	 */
	@ApiModelProperty(value = "图文内容")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CHAPTER_CONTENT")
	private String chapterContent;
	
	@ApiModelProperty(value = "章节类型（1、图文 2、视频）")
	@Column(name = "CHAPTER_TYPE")
	private Integer chapterType;
	
	@ApiModelProperty(value = "排序")
	@Column(name = "SORT")
	private Integer sort;
	
}
