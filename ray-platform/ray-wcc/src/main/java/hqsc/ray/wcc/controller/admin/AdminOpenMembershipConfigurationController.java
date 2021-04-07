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
package hqsc.ray.wcc.controller.admin;

import hqsc.ray.core.auth.annotation.PreAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.jpa.dto.OpenMembershipConfigurationDto;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.form.OpenMembershipConfigurationForm;
import hqsc.ray.wcc.jpa.service.OpenMembershipConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 开通会员配置controller
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-open-membership-configuration/manage/")
@Api(value = "开通会员配置表", tags = "开通会员配置表接口")
public class AdminOpenMembershipConfigurationController extends BaseController {
	
	private final OpenMembershipConfigurationService openMembershipConfigurationService;
	
	/**
	 * 分页列表
	 *
	 * @param openMembershipConfigurationForm
	 * @return Result
	 */
	@PreAuth
	@Log(value = "获取开通会员的配置列表", exception = "获取开通会员的配置列表请求异常")
	@GetMapping("/page")
	@ApiOperation(value = "获取开通会员的配置列表", notes = "分页查询")
	public Result<PageMap<OpenMembershipConfigurationDto>> page(OpenMembershipConfigurationForm openMembershipConfigurationForm) {
		PageMap<OpenMembershipConfigurationDto> map = openMembershipConfigurationService.listOpenMembershipConfigurations(openMembershipConfigurationForm);
		return Result.data(map);
	}
	
	/**
	 * 用户已购课程表信息
	 *
	 * @param id Id
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户已购课程表信息", exception = "用户已购课程表信息请求异常")
	@GetMapping("/get")
	@ApiOperation(value = "用户已购课程表信息", notes = "根据ID查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
	})
	public Result<?> get(@RequestParam String id) {
//		return Result.data(wccUserPurchasedCourseService.getById(id));
		return null;
	}
	
	/**
	 * 保存更新配置
	 *
	 * @param openMembershipConfigurationForm 对象
	 * @return Result
	 */
	@PreAuth
	@Log(value = "保存更新配置", exception = "保存更新配置请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "保存更新配置", notes = "保存更新配置")
	public Result<?> set(@Valid @RequestBody OpenMembershipConfigurationForm openMembershipConfigurationForm) {
		return openMembershipConfigurationService.save(openMembershipConfigurationForm);
//		return Result.condition(wccUserPurchasedCourseService.saveOrUpdate(wccUserPurchasedCourse));
	}
	
	/**
	 * 用户已购课程表删除
	 *
	 * @param ids id字符串，根据,号分隔
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户已购课程表删除", exception = "用户已购课程表删除请求异常")
	@PostMapping("/del")
	@ApiOperation(value = "用户已购课程表删除", notes = "用户已购课程表删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
	})
	public Result<?> del(@RequestParam String ids) {
//		return Result.condition(wccUserPurchasedCourseService.removeByIds(CollectionUtil.stringToCollection(ids)));
		return null;
	}
}

