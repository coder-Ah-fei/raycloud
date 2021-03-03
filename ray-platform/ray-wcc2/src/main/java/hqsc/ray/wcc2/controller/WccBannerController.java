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
import hqsc.ray.wcc2.form.WccBannerForm;
import hqsc.ray.wcc2.service.WccBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * banner图
 *
 * @author yang
 * @date 2021年3月1日
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-banner")
@Api(value = "banner图", tags = "banner图")
public class WccBannerController extends BaseController {
	
	@Autowired
	private WccBannerService wccBannerService;
	
	@PreAuth
	@Log(value = "获取消息列表", exception = "获取消息列表请求异常")
	@PostMapping(value = "/listWccBanners", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取消息列表", notes = "获取消息列表")
	public ResultMap<?> listWccBanners(WccBannerForm wccBannerForm) {
		ResultMap resultMap = wccBannerService.listWccBanners(wccBannerForm);
		return resultMap;
	}
	
}

