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
package hqsc.ray.wcc.vo;

import hqsc.ray.wcc.entity.WccResponseDetails;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 回答详情表实体类
 *
 * @author pangu
 * @since 2020-12-30
 */
@Data

@ApiModel(value = "WccResponseDetailsVO", description = "回答详情表")
public class WccResponseDetailsVO extends WccResponseDetails {
	
	@ApiModelProperty(value = "用户昵称")
	private String userNickname;
	@ApiModelProperty(value = "（被评论的）用户昵称")
	private String repliedUserNickname;
	@ApiModelProperty(value = "微信头像地址")
	private String wechatHeadPortraitAddress;
	@ApiModelProperty(value = "下级评论的数量")
	private Integer childNum;
	
	@ApiModelProperty(value = "下级评论（递归结构）")
	List<WccResponseDetailsVO> childList;
	
	@ApiModelProperty(value = "下级评论（非递归）")
	List<WccResponseDetailsVO> childList2;
	
}
