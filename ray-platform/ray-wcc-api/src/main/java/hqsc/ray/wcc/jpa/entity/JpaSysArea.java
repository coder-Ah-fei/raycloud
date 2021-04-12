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
import hqsc.ray.wcc.jpa.entity.base.BasicSysEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * 系统接口表实体类
 *
 * @author pangu
 * @since 2020-10-14
 */
@ApiModel(value = "JpaSysArea对象", description = "行政地区表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_area")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_area", comment = "行政地区表")
public class JpaSysArea extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	
	@ApiModelProperty(value = "等级")
	@Column(name = "level", columnDefinition = "char(1) default '' comment '等级'")
	private Integer level;
	
	/**
	 * 行政区划唯一标识
	 */
	@ApiModelProperty(value = "行政区划唯一标识")
	@Column(name = "adcode", columnDefinition = "varchar(6) null comment '行政区划唯一标识'")
	private String adcode;
	/**
	 * 简称，如“内蒙古”
	 */
	@ApiModelProperty(value = "简称，如“内蒙古”")
	@Column(name = "name", columnDefinition = "varchar(100) null comment '简称，如“内蒙古”'")
	private String name;
	/**
	 * 全称，如“内蒙古自治区”
	 */
	@ApiModelProperty(value = "全称，如“内蒙古自治区”")
	@Column(name = "fullname", columnDefinition = "varchar(100) null comment '全称，如“内蒙古自治区”'")
	private String fullname;
	
	@ApiModelProperty(value = "省")
	@Column(name = "province", columnDefinition = "varchar(50) null comment '省'")
	private String province;
	@ApiModelProperty(value = "市")
	@Column(name = "city", columnDefinition = "varchar(50) null comment '市'")
	private String city;
	@ApiModelProperty(value = "县")
	@Column(name = "county", columnDefinition = "varchar(50) null comment '县'")
	private String county;
	
	@ApiModelProperty(value = "上级")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId", referencedColumnName = "id")
	@JsonIgnore
	private JpaSysArea parent;
	
	/**
	 * 下级
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	private List<JpaSysArea> children;
	
	
	/**
	 * 经纬度
	 */
	@ApiModelProperty(value = "经纬度")
	@Column(name = "location", columnDefinition = "varchar(255) null comment '经纬度'")
	private String location;
	/**
	 * 行政区划拼音
	 */
	@ApiModelProperty(value = "行政区划拼音")
	@Column(name = "pinyin", columnDefinition = "varchar(100) null comment '行政区划拼音'")
	private String pinyin;
}
