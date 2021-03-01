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
 * 用户支付记录表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccPayLog对象", description = "用户支付记录表")
public class WccPayLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "PAY_LOG_ID", type = IdType.AUTO)
	private Long payLogId;
	/**
	* 订单号
	*/
	@ApiModelProperty(value = "订单号")
	@TableField("ORDER_ID")
	private Long orderId;
	/**
	* 第三方订单id
	*/
	@ApiModelProperty(value = "第三方订单id")
	@TableField("THIRDPARTY_ID")
	private Long thirdpartyId;
	/**
	* 用户id
	*/
	@ApiModelProperty(value = "用户id")
	@TableField("USER_ID")
	private Long userId;
	/**
	* 支付类型(0课程1会员)
	*/
	@ApiModelProperty(value = "支付类型(0课程1会员)")
	@TableField("PAY_TYPE")
	private Integer payType;
	/**
	* 所属id
	*/
	@ApiModelProperty(value = "所属id")
	@TableField("BELONG_ID")
	private Long belongId;
	/**
	* 商品原价
	*/
	@ApiModelProperty(value = "商品原价")
	@TableField("PROTO_PRICE")
	private BigDecimal protoPrice;
	/**
	* 支付金额
	*/
	@ApiModelProperty(value = "支付金额")
	@TableField("PAY_AMOUNT")
	private BigDecimal payAmount;
	/**
	* 支付时间
	*/
	@ApiModelProperty(value = "支付时间")
	@TableField("PAY_TIME")
	private LocalDateTime payTime;
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
