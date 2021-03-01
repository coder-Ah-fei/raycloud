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
 * 回答详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccResponseDetails对象", description = "回答详情表")
public class WccResponseDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@ApiModelProperty(value = "主键id")
	@TableId(value = "RESPONSE_DETAIL_ID", type = IdType.AUTO)
	private Long responseDetailId;
	/**
	* 回答用户
	*/
	@ApiModelProperty(value = "回答用户")
	@TableField("USER_ID")
	private Long userId;
	/**
	* 回答时间
	*/
	@ApiModelProperty(value = "回答时间")
	@TableField("RESPONSE_TIME")
	private LocalDateTime responseTime;
	/**
	* 回答内容
	*/
	@ApiModelProperty(value = "回答内容")
	@TableField("RESPONSE_BODY")
	private String responseBody;
	/**
	* 所属类型(0回复1提问2文章3话题4视频)
	*/
	@ApiModelProperty(value = "所属类型(0回复1提问2文章3话题4视频)")
	@TableField("BELONG_TYPE")
	private Integer belongType;
	/**
	* 所属id
	*/
	@ApiModelProperty(value = "所属id")
	@TableField("BELONG_ID")
	private Long belongId;
	/**
	* 上级id
	*/
	@ApiModelProperty(value = "上级id")
	@TableField("PARENT_ID")
	private Long parentId;
	/**
	* 收藏数
	*/
	@ApiModelProperty(value = "收藏数")
	@TableField("FAVORITE_COUNT")
	private Integer favoriteCount;
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
