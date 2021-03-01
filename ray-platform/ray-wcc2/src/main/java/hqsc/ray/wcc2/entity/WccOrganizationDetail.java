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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 机构详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@ApiModel(value = "WccOrganizationDetail对象", description = "机构详情表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "wcc_organization_detail")
@org.hibernate.annotations.Table(appliesTo = "wcc_organization_detail", comment = "机构详情表")
public class WccOrganizationDetail extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	@Id
	@Column(name = "ORGANIZATION_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc2.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 机构图标
	 */
	@ApiModelProperty(value = "机构图标")
	@OneToOne
	@JoinColumn(name = "ICON", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private WccAttachment icon;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(value = "机构名称")
	@Column(name = "O_NAME")
	private String OName;
	/**
	 * 机构坐标
	 */
	@ApiModelProperty(value = "机构坐标")
	@Column(name = "ADDRESS")
	private String address;
	/**
	 * 领域
	 */
	@ApiModelProperty(value = "领域")
	@Column(name = "FIELD")
	private String field;
	/**
	 * 结算方式
	 */
	@ApiModelProperty(value = "结算方式")
	@Column(name = "CLEARING_FORM")
	private String clearingForm;
	/**
	 * 注册人数
	 */
	@ApiModelProperty(value = "注册人数")
	@Column(name = "REGIST_COUNT")
	private Integer registCount;
	/**
	 * 平台网址
	 */
	@ApiModelProperty(value = "平台网址")
	@Column(name = "PLATFORM_WEB_LINK")
	private String platformWebLink;
	/**
	 * 平台介绍
	 */
	@ApiModelProperty(value = "平台介绍")
	@Column(name = "PLATFORM_INTRODUCE")
	private String platformIntroduce;
	/**
	 * 机构相关人员姓名
	 */
	@ApiModelProperty(value = "机构相关人员姓名")
	@Column(name = "NAME")
	private String name;
	/**
	 * 机构相关类目
	 */
	@ApiModelProperty(value = "机构相关类目")
	@Column(name = "TYPE")
	private String type;
	/**
	 * 机构相关类目
	 */
	@ApiModelProperty(value = "机构相关人员职位")
	@Column(name = "POSITION")
	private String position;
	
	
}
