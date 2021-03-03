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

import hqsc.ray.wcc2.common.enums.BannerPosition;
import hqsc.ray.wcc2.entity.base.BasicEntity;
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
 * banner图管理表
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccBanner对象", description = "banner图管理表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_banner")
@org.hibernate.annotations.Table(appliesTo = "wcc_banner", comment = "banner图管理表")
public class WccBanner extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "BANNER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	
	@ApiModelProperty(value = "图片")
	@OneToOne
	@JoinColumn(name = "RESOURCE_ID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private RaySysAttachment resource;
	
	@ApiModelProperty(value = "banner图名称")
	@Column(name = "BANNER_NAME")
	private String bannerName;
	
	@ApiModelProperty(value = "banner图描述")
	@Column(name = "DEPICT")
	private String depict;
	
	@ApiModelProperty(value = "点击banner图访问的地址")
	@Column(name = "URL")
	private String url;
	
	@ApiModelProperty(value = "排序  从大到小， 越大越靠前")
	@Column(name = "SORT", columnDefinition = "char default 0 comment '排序  从大到小， 越大越靠前'")
	private Integer sort;
	
	@ApiModelProperty(value = "banner图位置")
	@Column(name = "BANNER_POSITION", columnDefinition = "varchar(255) null comment 'banner图位置（XUE_JIAO_CHENG,JU_YOU_XUAN）'")
	@Enumerated(EnumType.STRING)
	private BannerPosition bannerPosition;
}
