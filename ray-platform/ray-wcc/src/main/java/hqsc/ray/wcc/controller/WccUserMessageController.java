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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.core.auth.annotation.PreAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.core.web.util.CollectionUtil;
import hqsc.ray.wcc.entity.WccUserMessage;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccUserMessageForm;
import hqsc.ray.wcc.jpa.service.WccUserMessageService;
import hqsc.ray.wcc.service.IWccUserMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 用户消息表 前端控制器
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-user-message")
@Api(value = "用户消息表", tags = "用户消息表接口")
public class WccUserMessageController extends BaseController {
	
	private final IWccUserMessageService iWccUserMessageService;
	private final WccUserMessageService wccUserMessageService;
	
	/**
	 * 分页列表
	 *
	 * @param page   分页信息
	 * @param search 　搜索关键词
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户消息表列表", exception = "用户消息表列表请求异常")
	@GetMapping("/page")
	@ApiOperation(value = "用户消息表列表", notes = "分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
			@ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
			@ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
			@ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
	})
	public Result<?> page(Page page, Map search) {
		return Result.data(iWccUserMessageService.listPage(page, search));
	}
	
	/**
	 * 用户消息表信息
	 *
	 * @param id Id
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户消息表信息", exception = "用户消息表信息请求异常")
	@GetMapping("/get")
	@ApiOperation(value = "用户消息表信息", notes = "根据ID查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
	})
	public Result<?> get(@RequestParam String id) {
		return Result.data(iWccUserMessageService.getById(id));
	}
	
	/**
	 * 用户消息表设置
	 *
	 * @param wccUserMessage WccUserMessage 对象
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户消息表设置", exception = "用户消息表设置请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "用户消息表设置", notes = "用户消息表设置,支持新增或修改")
	public Result<?> set(@Valid @RequestBody WccUserMessage wccUserMessage) {
		return Result.condition(iWccUserMessageService.saveOrUpdate(wccUserMessage));
	}
	
	/**
	 * 用户消息表删除
	 *
	 * @param ids id字符串，根据,号分隔
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户消息表删除", exception = "用户消息表删除请求异常")
	@PostMapping("/del")
	@ApiOperation(value = "用户消息表删除", notes = "用户消息表删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
	})
	public Result<?> del(@RequestParam String ids) {
		return Result.condition(iWccUserMessageService.removeByIds(CollectionUtil.stringToCollection(ids)));
	}
	//------------------------------------------------------------------------------
	
	@PreAuth
	@Log(value = "获取消息列表", exception = "获取消息列表请求异常")
	@PostMapping(value = "/listWccUserMessages", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取消息列表", notes = "获取消息列表")
	public ResultMap<?> listWccUserMessages(WccUserMessageForm wccUserMessageForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccUserMessageForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccUserMessageService.listWccUserMessages(wccUserMessageForm);
		return resultMap;
	}
	
	@PreAuth
	@Log(value = "获取消息详细列表", exception = "获取消息详细列表请求异常")
	@PostMapping(value = "/listWccUserMessageDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取消息详细列表", notes = "获取消息详细列表")
	public ResultMap<?> listWccUserMessageDetails(WccUserMessageForm wccUserMessageForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccUserMessageForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccUserMessageService.listWccUserMessageDetails(wccUserMessageForm);
		return resultMap;
	}
	
	/**
	 * 将消息设置为已读
	 *
	 * @param wccUserMessageForm
	 * @return
	 */
	@PreAuth
	@Log(value = "将消息设置为已读", exception = "将消息设置为已读请求异常")
	@PostMapping(value = "/setAsRead", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "将消息设置为已读", notes = "将消息设置为已读")
	public ResultMap<?> setAsRead(WccUserMessageForm wccUserMessageForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccUserMessageForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccUserMessageService.setAsRead(wccUserMessageForm);
		return resultMap;
	}
	
}

