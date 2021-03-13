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
import hqsc.ray.wcc.entity.WccReleaseInfo;
import hqsc.ray.wcc.form.WccReleaseInfoForm;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccReleaseInfoDto;
import hqsc.ray.wcc.jpa.service.WccReleaseInfoService;
import hqsc.ray.wcc.service.IWccReleaseInfoService;
import hqsc.ray.wcc.vo.MyReleaseInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 发布信息表 前端控制器
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-release-info")
@Api(value = "发布信息表", tags = "发布信息表接口")
public class WccReleaseInfoController extends BaseController {
	
	private final IWccReleaseInfoService iWccReleaseInfoService;
	private final WccReleaseInfoService wccReleaseInfoService;
	
	/**
	 * 分页列表
	 *
	 * @param page   分页信息
	 * @param search 　搜索关键词
	 * @return Result
	 */
	@PreAuth
	@Log(value = "发布信息表列表", exception = "发布信息表列表请求异常")
	@GetMapping("/page")
	@ApiOperation(value = "发布信息表列表", notes = "分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
			@ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
			@ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
			@ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
	})
	public Result<?> page(Page page, Map search) {
		return Result.data(iWccReleaseInfoService.listPage(page, search));
	}
	
	/**
	 * 发布信息表信息
	 *
	 * @param id Id
	 * @return Result
	 */
	@Log(value = "发布信息表信息", exception = "发布信息表信息请求异常")
	@PostMapping("/get")
	@ApiOperation(value = "发布信息表信息", notes = "根据ID查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
	})
	public Result<?> get(@RequestParam String id) {
		return Result.data(iWccReleaseInfoService.getById(id));
	}
	
	/**
	 * 发布信息表设置
	 *
	 * @param wccReleaseInfo WccReleaseInfo 对象
	 * @return Result
	 */
	@Log(value = "发布信息表设置", exception = "发布信息表设置请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "发布信息表设置", notes = "发布信息表设置,支持新增或修改")
	public Result<?> set(@Valid @RequestBody WccReleaseInfo wccReleaseInfo) {
		return Result.condition(iWccReleaseInfoService.saveOrUpdate(wccReleaseInfo));
	}
	
	/**
	 * 发布信息表删除
	 *
	 * @param ids id字符串，根据,号分隔
	 * @return Result
	 */
	@PreAuth
	@Log(value = "发布信息表删除", exception = "发布信息表删除请求异常")
	@PostMapping("/del")
	@ApiOperation(value = "发布信息表删除", notes = "发布信息表删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
	})
	public Result<?> del(@RequestParam String ids) {
		return Result.condition(iWccReleaseInfoService.removeByIds(CollectionUtil.stringToCollection(ids)));
	}
	
	//-----------------------------------------------
	
	/**
	 * 我的页面，获取用户发布的内容
	 *
	 * @param page               分页信息
	 * @param wccReleaseInfoForm 　搜索关键词
	 * @return Result
	 */
	@PreAuth
	@Log(value = "我的页面，获取用户发布的内容", exception = "发布信息表列表请求异常")
	@PostMapping("/listWccReleaseInfos")
	@ApiOperation(value = "发布信息表列表", notes = "分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
			@ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
			@ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
			@ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
	})
	public Result<?> listWccReleaseInfos(Page page, WccReleaseInfoForm wccReleaseInfoForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccReleaseInfoForm.setBelongUserId(Long.valueOf(userInfo.getUserId()));
//		IPage<WccReleaseInfo> wccReleaseInfoIPage = wccReleaseInfoService.listWccReleaseInfos(page, wccReleaseInfoForm);
		List<MyReleaseInfoVO> myReleaseInfo = iWccReleaseInfoService.findMyReleaseInfo(wccReleaseInfoForm, page.getCurrent(), page.getSize());
		return Result.data(myReleaseInfo);
	}
	
	
	//-----------------------------------------------------------------------------------
	
	@PreAuth
	@Log(value = "获取消息列表", exception = "获取消息列表请求异常")
	@PostMapping(value = "/listWccUserMessages", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取消息列表", notes = "获取消息列表")
	public ResultMap<?> listWccUserMessages(hqsc.ray.wcc.jpa.form.WccReleaseInfoForm wccReleaseInfoForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccReleaseInfoForm.setBelongUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccReleaseInfoService.listWccReleaseInfos(wccReleaseInfoForm);
		return resultMap;
	}
	
	
	@Log(value = "获取圈子中的发布信息", exception = "获取圈子中的发布信息请求异常")
	@PostMapping("/listByCircleId")
	@ApiOperation(value = "获取圈子中的发布信息", notes = "分页查询")
	public Result<ResultMap<WccReleaseInfoDto>> listByCircleId(hqsc.ray.wcc.jpa.form.WccReleaseInfoForm wccReleaseInfoForm) {
		ResultMap resultMap = wccReleaseInfoService.listWccReleaseInfos(wccReleaseInfoForm);
		return Result.data(resultMap);
	}
}

