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

import javax.persistence.*;

/**
 * 用户关注关联表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccUserConcern对象", description = "用户关注关联表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_user_concern")
@org.hibernate.annotations.Table(appliesTo = "wcc_user_concern", comment = "用户关注关联表")
public class JpaWccUserConcern extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "USER_CONCERN_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private JpaWccUser jpaWccUser;
	
	/**
	 * 所属id(被关注的用户)
	 */
	@ApiModelProperty(value = "所属id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BELONG_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private JpaWccUser belongUser;
	/**
	 * 访问次数
	 */
	@ApiModelProperty(value = "访问次数")
	@Column(name = "ACCESS_COUNT")
	private Integer accessCount;
	
	
}
