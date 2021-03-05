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
 * 圈子信息表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccCircleInfo对象", description = "圈子信息表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_circle_info")
@org.hibernate.annotations.Table(appliesTo = "wcc_circle_info", comment = "圈子信息表")
public class JpaWccCircleInfo extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "CIRCLE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	
	/**
	 * 圈子名称
	 */
	@ApiModelProperty(value = "圈子名称")
	@Column(name = "CIRCLE_NAME")
	private String circleName;
	/**
	 * 圈子图片
	 */
	@ApiModelProperty(value = "圈子图片")
	@OneToOne
	@JoinColumn(name = "CIRCLE_IMG", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaSysAttachment circleImg;
	
	
	/**
	 * 圈子简介
	 */
	@ApiModelProperty(value = "圈子简介")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CIRCLE_SYNOPSIS")
	private String circleSynopsis;
	
	
}
