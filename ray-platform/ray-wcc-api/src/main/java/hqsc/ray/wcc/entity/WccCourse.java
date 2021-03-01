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

import java.io.Serializable;
import java.math.BigDecimal;
import hqsc.ray.core.database.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 课程表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccCourse对象", description = "课程表")
public class WccCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "COURSE_ID", type = IdType.AUTO)
	private Long courseId;
	/**
	* 课程标题
	*/
	@ApiModelProperty(value = "课程标题")
	@TableField("COURSE_TITEL")
	private String courseTitel;
	/**
	* 课程附件
	*/
	@ApiModelProperty(value = "课程附件")
	@TableField("COURSE_ATTACHMENT")
	private Long courseAttachment;
	/**
	* 课程类型(0系统课1进阶课)
	*/
	@ApiModelProperty(value = "课程类型(0系统课1进阶课)")
	@TableField("COURSE_TYPE")
	private Integer courseType;
	/**
	* 课程现价
	*/
	@ApiModelProperty(value = "课程现价")
	@TableField("CURRENT_PRICE")
	private BigDecimal currentPrice;
	/**
	* 课程原价
	*/
	@ApiModelProperty(value = "课程原价")
	@TableField("ORIGINAL_PRICE")
	private BigDecimal originalPrice;
	/**
	* 会员是否免费(0免费1收费)
	*/
	@ApiModelProperty(value = "会员是否免费(0免费1收费)")
	@TableField("VIP_IS_FREE")
	private Integer vipIsFree;
	/**
	* 会员收费价格
	*/
	@ApiModelProperty(value = "会员收费价格")
	@TableField("VIP_PRICE")
	private BigDecimal vipPrice;
	/**
	* 销售数量
	*/
	@ApiModelProperty(value = "销售数量")
	@TableField("SELL_COUNT")
	private Integer sellCount;
	/**
	* 课程标题介绍
	*/
	@ApiModelProperty(value = "课程标题介绍")
	@TableField("COURSE_TITEL_INTRODUCE")
	private String courseTitelIntroduce;
	/**
	* 课程内容介绍
	*/
	@ApiModelProperty(value = "课程内容介绍")
	@TableField("COURSE_BODY_INTRODUCE")
	private String courseBodyIntroduce;
	/**
	* 连载状态(0已完结1连载中)
	*/
	@ApiModelProperty(value = "连载状态(0已完结1连载中)")
	@TableField("IS_SERIAL")
	private Integer isSerial;
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
