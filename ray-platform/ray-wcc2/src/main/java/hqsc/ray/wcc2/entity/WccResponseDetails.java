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
import java.util.Date;

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
public class WccResponseDetails extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "RESPONSE_DETAIL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 回答用户
	 */
	@ApiModelProperty(value = "回答用户")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnore
	private WccUser wccUser;
	/**
	 * 回答时间
	 */
	@ApiModelProperty(value = "回答时间")
	@Column(name = "RESPONSE_TIME")
	private Date responseTime;
	/**
	 * 回答内容
	 */
	@ApiModelProperty(value = "回答内容")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "RESPONSE_BODY")
	private String responseBody;
	/**
	 * 所属类型(0回复1提问2文章3话题4视频)
	 */
	@ApiModelProperty(value = "所属类型(0回复1提问2文章3话题4视频)")
	@Column(name = "BELONG_TYPE")
	private Integer belongType;
	/**
	 * 所属id
	 */
	@ApiModelProperty(value = "所属id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BELONG_ID", referencedColumnName = "RELEASE_INFO_ID")
	@JsonIgnore
	private WccReleaseInfo wccReleaseInfo;
	
	/**
	 * 上级id
	 */
	@ApiModelProperty(value = "上级id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID", referencedColumnName = "RESPONSE_DETAIL_ID")
	@JsonIgnore
	private WccResponseDetails wccResponseDetails;
	
	/**
	 * 收藏数
	 */
	@ApiModelProperty(value = "收藏数")
	@Column(name = "FAVORITE_COUNT")
	private Integer favoriteCount;
	
}
