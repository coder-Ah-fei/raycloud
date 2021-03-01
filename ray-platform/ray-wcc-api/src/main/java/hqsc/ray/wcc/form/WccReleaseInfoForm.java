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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 发布信息表form
 *
 * @author pangu
 * @since 2020-12-30
 */

@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "WccReleaseInfo对象", description = "发布信息表")
public class WccReleaseInfoForm extends Page {
	
	
	@ApiModelProperty(value = "主键list")
	private List<Long> releaseInfoIds;
	
	@ApiModelProperty(value = "当前登录用户id")
	private Long userId;
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long releaseInfoId;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String titel;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;
	/**
	 * 附件id
	 */
	@ApiModelProperty(value = "附件id")
	private Long attachmentId;
	/**
	 * 发布类型(0提问1话题2文章3视频)
	 */
	@ApiModelProperty(value = "发布类型")
	private Integer type;
	/**
	 * 所属用户
	 */
	@ApiModelProperty(value = "所属用户")
	private Long belongUserId;
	
	@ApiModelProperty(value = "收藏用户的id")
	private Long favoritesUserId;
	
	/**
	 * 所属圈子
	 */
	@ApiModelProperty(value = "所属圈子")
	private Long belongCircleId;
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
