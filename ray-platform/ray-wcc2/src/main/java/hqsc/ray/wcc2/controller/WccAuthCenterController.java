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
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccAuthCenterForm;
import hqsc.ray.wcc2.service.WccAuthCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证中心控制器
 *
 * @author yang
 * @date 2021年3月1日
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-auth-center")
@Api(value = "用户认证中心", tags = "用户认证中心")
public class WccAuthCenterController extends BaseController {
	
	@Autowired
	private WccAuthCenterService wccAuthCenterService;
	
	/**
	 * 主播证报名
	 *
	 * @param wccAuthCenterForm
	 * @return Result
	 */
	@PreAuth
	@Log(value = "主播证报名", exception = "主播证报名请求异常")
	@PostMapping(value = "/anchorInfoSubmit", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "主播证报名请求异常", notes = "主播证报名请求异常")
	public ResultMap<?> anchorInfoSubmit(WccAuthCenterForm wccAuthCenterForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccAuthCenterForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccAuthCenterService.anchorInfoSubmit(wccAuthCenterForm);
		return resultMap;
	}
	
	@PreAuth
	@Log(value = "是否已经主播证报名", exception = "是否已经主播证报名请求异常")
	@PostMapping(value = "/isAnchorInfoSubmit", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "是否已经主播证报名", notes = "是否已经主播证报名")
	public ResultMap<?> anchorInfoSubmit() {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		ResultMap resultMap = wccAuthCenterService.isAnchorInfoSubmit(Long.valueOf(userInfo.getUserId()));
		return resultMap;
	}
	
	
}

