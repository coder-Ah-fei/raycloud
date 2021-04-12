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
package hqsc.ray.wcc.controller;

import hqsc.ray.core.auth.annotation.PreAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.util.PemUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.jpa.dto.OpenVipConfigurationDto;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.form.OpenVipConfigurationForm;
import hqsc.ray.wcc.jpa.service.OpenVipConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * 开通会员配置controller
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-open-vip-configuration")
@Api(value = "开通会员配置表", tags = "开通会员配置表接口")
public class OpenVipConfigurationController extends BaseController {
	
	private final OpenVipConfigurationService openVipConfigurationService;
	
	/**
	 * 分页列表
	 *
	 * @param openVipConfigurationForm
	 * @return Result
	 */
	@PreAuth
	@Log(value = "获取开通会员的配置列表", exception = "获取开通会员的配置列表请求异常")
	@PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取开通会员的配置列表", notes = "分页查询")
	public Result<PageMap<OpenVipConfigurationDto>> page(OpenVipConfigurationForm openVipConfigurationForm) {
		
		openVipConfigurationForm.setStatus(1)
				.setIsDelete(0);
		PageMap<OpenVipConfigurationDto> map = openVipConfigurationService.listOpenMembershipConfigurations(openVipConfigurationForm);
		return Result.data(map);
	}
	
}

