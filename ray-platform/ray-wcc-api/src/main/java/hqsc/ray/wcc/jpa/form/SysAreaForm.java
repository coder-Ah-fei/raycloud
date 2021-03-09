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
package hqsc.ray.wcc.jpa.form;

import hqsc.ray.wcc.jpa.form.basic.BaseForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 系统接口表实体类
 *
 * @author pangu
 * @since 2020-10-14
 */
@Getter
@Setter
@Accessors(chain = true)
public class SysAreaForm extends BaseForm {
	
	
	private Long id;
	
	/**
	 * 行政区划唯一标识
	 */
	@ApiModelProperty(value = "行政区划唯一标识")
	private String adcode;
	/**
	 * 简称，如“内蒙古”
	 */
	@ApiModelProperty(value = "简称，如“内蒙古”")
	private String name;
	/**
	 * 全称，如“内蒙古自治区”
	 */
	@ApiModelProperty(value = "全称，如“内蒙古自治区”")
	private String fullname;
	
	@ApiModelProperty(value = "省")
	private String province;
	@ApiModelProperty(value = "市")
	private String city;
	@ApiModelProperty(value = "县")
	private String county;
	
	@ApiModelProperty(value = "上级")
	private Long parentId;
	
	/**
	 * 经纬度
	 */
	@ApiModelProperty(value = "经纬度")
	private String location;
	/**
	 * 行政区划拼音
	 */
	@ApiModelProperty(value = "行政区划拼音")
	private String pinyin;
}
