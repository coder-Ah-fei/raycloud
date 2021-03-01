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
package hqsc.ray.wcc.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 回答详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccResponseDetails对象", description = "回答详情表")
public class WccResponseDetailsForm {
	
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long responseDetailId;
	/**
	 * 回答用户
	 */
	@ApiModelProperty(value = "回答用户")
	private Long userId;
	/**
	 * 回答时间
	 */
	@ApiModelProperty(value = "回答时间")
	private LocalDateTime responseTime;
	/**
	 * 回答内容
	 */
	@ApiModelProperty(value = "回答内容")
	private String responseBody;
	/**
	 * 所属类型(0回复1提问2文章3话题4视频)
	 */
	@ApiModelProperty(value = "所属类型(0回复1提问2文章3话题4视频)")
	private Integer belongType;
	/**
	 * 所属id
	 */
	@ApiModelProperty(value = "所属id")
	private Long belongId;
	/**
	 * 上级id
	 */
	@ApiModelProperty(value = "上级id")
	private Long parentId;
	/**
	 * 收藏数
	 */
	@ApiModelProperty(value = "收藏数")
	private Integer favoriteCount;
	/**
	 * 状态（1正常 0禁用）
	 */
	@ApiModelProperty(value = "状态（1正常 0禁用）")
	private Integer status;
	/**
	 * 1 删除  0正常
	 */
	@ApiModelProperty(value = "1 删除  0正常")
	private Integer isDelete;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createdBy;
	/**
	 * 创建人姓名
	 */
	@ApiModelProperty(value = "创建人姓名")
	private String createdByUser;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime creationDate;
	/**
	 * 修改人姓名
	 */
	@ApiModelProperty(value = "修改人姓名")
	private String lastUpdatedByUser;
	/**
	 * 最后更新人
	 */
	@ApiModelProperty(value = "最后更新人")
	private String lastUpdatedBy;
	/**
	 * 最后更新时间
	 */
	@ApiModelProperty(value = "最后更新时间")
	private LocalDateTime lastUpdateDate;
	
}
