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
import hqsc.ray.core.web.util.CollectionUtil;
import hqsc.ray.wcc.jpa.form.WccUserForm;
import hqsc.ray.wcc.jpa.service.WccUserService;
import hqsc.ray.wcc.service.IWccUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-user/manage")
@Api(value = "用户表", tags = "用户表接口")
public class AdminWccUserController extends BaseController {
	
	private final IWccUserService iWccUserService;
	private final WccUserService userService;
	
	/**
	 * 分页列表
	 *
	 * @param wccUserForm 　搜索关键词
	 * @return Result
	 */
	@Log(value = "用户表列表", exception = "用户表列表请求异常")
	@GetMapping("/page")
	@ApiOperation(value = "用户表列表", notes = "分页查询")
	public Result<?> page(WccUserForm wccUserForm) {
		return Result.data(userService.listWccUsers(wccUserForm));
	}
	
	/**
	 * 用户表信息
	 *
	 * @param id Id
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户表信息", exception = "用户表信息请求异常")
	@GetMapping("/get")
	@ApiOperation(value = "用户表信息", notes = "根据ID查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
	})
	public Result<?> get(@RequestParam String id) {
		return Result.data(iWccUserService.getById(id));
	}
	
	/**
	 * 用户表设置
	 *
	 * @param userForm WccUserForm 对象
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户表设置", exception = "用户表设置请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "用户表设置", notes = "用户表设置,支持新增或修改")
	public Result<?> set(@Valid @RequestBody WccUserForm userForm) {
		return userService.save(userForm);
	}
	
	/**
	 * 用户表删除
	 *
	 * @param ids id字符串，根据,号分隔
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户表删除", exception = "用户表删除请求异常")
	@PostMapping("/del")
	@ApiOperation(value = "用户表删除", notes = "用户表删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
	})
	public Result<?> del(@RequestParam String ids) {
		return Result.condition(iWccUserService.removeByIds(CollectionUtil.stringToCollection(ids)));
	}
	
	//-------------------------------------------------------------------------
	
	
}

