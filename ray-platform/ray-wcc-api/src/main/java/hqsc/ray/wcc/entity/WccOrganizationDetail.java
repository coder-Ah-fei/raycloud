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
package hqsc.ray.wcc.entity;

import hqsc.ray.core.database.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 机构详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccOrganizationDetail对象", description = "机构详情表")
public class WccOrganizationDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "ORGANIZATION_ID", type = IdType.AUTO)
	private Long organizationId;
	/**
	* 机构图标
	*/
	@ApiModelProperty(value = "机构图标")
	@TableField("ICON")
	private Long icon;
	/**
	* 机构名称
	*/
	@ApiModelProperty(value = "机构名称")
	@TableField("O_NAME")
	private String OName;
	/**
	 * 机构坐标
	 */
	@ApiModelProperty(value = "机构坐标")
	@TableField("ADDRESS")
	private String address;
	/**
	 * 领域
	 */
	@ApiModelProperty(value = "领域")
	@TableField("FIELD")
	private String field;
	/**
	* 结算方式
	*/
	@ApiModelProperty(value = "结算方式")
	@TableField("CLEARING_FORM")
	private String clearingForm;
	/**
	* 注册人数
	*/
	@ApiModelProperty(value = "注册人数")
	@TableField("REGIST_COUNT")
	private Integer registCount;
	/**
	* 平台网址
	*/
	@ApiModelProperty(value = "平台网址")
	@TableField("PLATFORM_WEB_LINK")
	private String platformWebLink;
	/**
	* 平台介绍
	*/
	@ApiModelProperty(value = "平台介绍")
	@TableField("PLATFORM_INTRODUCE")
	private String platformIntroduce;
	/**
	* 机构相关人员姓名
	*/
	@ApiModelProperty(value = "机构相关人员姓名")
	@TableField("NAME")
	private String name;
	/**
	* 机构相关类目
	*/
	@ApiModelProperty(value = "机构相关类目")
	@TableField("TYPE")
	private String type;
	/**
	 * 机构相关类目
	 */
	@ApiModelProperty(value = "机构相关人员职位")
	@TableField("POSITION")
	private String position;
	/**
	* 状态（1正常 0禁用）
	*/
	@ApiModelProperty(value = "状态（1正常 0禁用）")
	@TableField("STATUS")
	private Integer status;
	/**
	* 1 删除  0正常
	*/
	@ApiModelProperty(value = "1 删除  0正常")
	@TableField("IS_DELETE")
	private Integer isDelete;
	/**
	* 创建人
	*/
	@ApiModelProperty(value = "创建人")
	@TableField("CREATED_BY")
	private String createdBy;
	/**
	* 创建人姓名
	*/
	@ApiModelProperty(value = "创建人姓名")
	@TableField("CREATED_BY_USER")
	private String createdByUser;
	/**
	* 创建时间
	*/
	@ApiModelProperty(value = "创建时间")
	@TableField("CREATION_DATE")
	private LocalDateTime creationDate;
	/**
	* 修改人姓名
	*/
	@ApiModelProperty(value = "修改人姓名")
	@TableField("LAST_UPDATED_BY_USER")
	private String lastUpdatedByUser;
	/**
	* 最后更新人
	*/
	@ApiModelProperty(value = "最后更新人")
	@TableField("LAST_UPDATED_BY")
	private String lastUpdatedBy;
	/**
	* 最后更新时间
	*/
	@ApiModelProperty(value = "最后更新时间")
	@TableField("LAST_UPDATE_DATE")
	private LocalDateTime lastUpdateDate;


}
