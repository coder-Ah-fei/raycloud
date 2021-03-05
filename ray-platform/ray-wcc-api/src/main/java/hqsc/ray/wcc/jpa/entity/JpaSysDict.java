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

import hqsc.ray.wcc.jpa.entity.base.BasicSysEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 字典表实体类
 *
 * @author pangu
 * @since 2020-07-11
 */
@ApiModel(value = "SysDict对象", description = "字典表")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ray_sys_dict")
@org.hibernate.annotations.Table(appliesTo = "ray_sys_dict", comment = "字典表")
public class JpaSysDict extends BasicSysEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
	@GenericGenerator(name = "myid", strategy = "hqsc.ray.wcc.jpa.configs.MyInsertGenerator")
	private Long id;
	/**
	 * 父主键
	 */
	@ApiModelProperty(value = "父主键")
	@Column(name = "parent_id")
	private Long parentId;
	/**
	 * 字典码
	 */
	@ApiModelProperty(value = "字典码")
	private String code;
	/**
	 * 字典值
	 */
	@ApiModelProperty(value = "字典值")
	@Column(name = "dict_key")
	private String dictKey;
	/**
	 * 字典名称
	 */
	@ApiModelProperty(value = "字典名称")
	@Column(name = "dict_value")
	private String dictValue;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 字典备注
	 */
	@ApiModelProperty(value = "字典备注")
	private String remark;
	
	
}
