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
 * 发布信息表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccReleaseInfo对象", description = "发布信息表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_release_info")
@org.hibernate.annotations.Table(appliesTo = "wcc_release_info", comment = "发布信息表")
public class JpaWccReleaseInfo extends BasicEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "RELEASE_INFO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	@Column(name = "TITEL")
	private String titel;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CONTENT")
	private String content;
	
	/**
	 * 附件id
	 */
	@ApiModelProperty(value = "附件id")
	@OneToOne
	@JoinColumn(name = "ATTACHMENT_ID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private JpaWccAttachment jpaWccAttachment;
	
	
	/**
	 * 发布类型(0提问1话题2文章3视频)
	 */
	@ApiModelProperty(value = "发布类型")
	@Column(name = "TYPE")
	private Long type;
	/**
	 * 所属用户
	 */
	@ApiModelProperty(value = "所属用户")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BELONG_USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private JpaWccUser belongUser;
	/**
	 * 所属圈子
	 */
	@ApiModelProperty(value = "所属圈子")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BELONG_CIRCLE_ID", referencedColumnName = "CIRCLE_ID")
	@JsonIgnore
	private JpaWccCircleInfo belongCircle;
	
}
