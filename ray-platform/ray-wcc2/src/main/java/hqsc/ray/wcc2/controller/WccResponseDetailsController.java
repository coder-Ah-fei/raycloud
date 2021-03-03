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
package hqsc.ray.wcc2.controller;

import hqsc.ray.core.auth.annotation.PreAuth;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccResponseDetailsForm;
import hqsc.ray.wcc2.form.WccUserMessageForm;
import hqsc.ray.wcc2.service.WccResponseDetailsService;
import hqsc.ray.wcc2.service.WccUserMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息控制器
 *
 * @author yang
 * @date 2021年3月1日
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-response-details")
@Api(value = "消息控制器", tags = "消息控制器")
public class WccResponseDetailsController extends BaseController {
	
	@Autowired
	private WccResponseDetailsService wccResponseDetailsService;
	
	@PreAuth
	@Log(value = "新增评论", exception = "新增评论请求异常")
	@PostMapping(value = "/saveWccResponseDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "新增评论", notes = "新增评论")
	public ResultMap<?> listWccUserMessages(WccResponseDetailsForm wccResponseDetailsForm) {
		ResultMap resultMap = wccResponseDetailsService.saveWccResponseDetails(wccResponseDetailsForm);
		return resultMap;
	}
	
	
}

