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
import java.time.LocalDateTime;

/**
 * 回答详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccResponseDetails对象", description = "回答详情表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_response_details")
@org.hibernate.annotations.Table(appliesTo = "wcc_response_details", comment = "回答详情表")
public class JpaWccResponseDetails extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "RESPONSE_DETAIL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 回答用户
	 */
	@ApiModelProperty(value = "回答用户")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private JpaWccUser jpaWccUser;
	/**
	 * 回答时间
	 */
	@ApiModelProperty(value = "回答时间")
	@Column(name = "RESPONSE_TIME")
	private LocalDateTime responseTime;
	/**
	 * 回答内容
	 */
	@ApiModelProperty(value = "回答内容")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "RESPONSE_BODY")
	private String responseBody;
	/**
	 * old:所属类型(0回复1提问2文章3话题4视频5课程6章节)
	 * new:所属类型(0发布信息1课程2章节)
	 */
	@ApiModelProperty(value = "所属类型(0发布信息1课程2章节)")
	@Column(name = "BELONG_TYPE")
	private Integer belongType;
	/**
	 * 所属id
	 */
	@ApiModelProperty(value = "所属id")
	@Column(name = "BELONG_ID")
	private Long belongId;
	
	/**
	 * 上级id
	 */
	@ApiModelProperty(value = "上级id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID", referencedColumnName = "RESPONSE_DETAIL_ID")
	@JsonIgnore
	private JpaWccResponseDetails jpaWccResponseDetails;
	
	/**
	 * 收藏数
	 */
	@ApiModelProperty(value = "收藏数")
	@Column(name = "FAVORITE_COUNT")
	private Integer favoriteCount;
	
}
