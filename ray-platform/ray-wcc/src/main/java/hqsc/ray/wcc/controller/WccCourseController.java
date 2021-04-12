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
import hqsc.ray.wcc.entity.WccCourse;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCourseDto;
import hqsc.ray.wcc.jpa.form.WccCourseForm;
import hqsc.ray.wcc.jpa.service.WccCourseService;
import hqsc.ray.wcc.service.IWccCourseService;
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
 * 课程表 前端控制器
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wccCourse")
@Api(value = "课程表", tags = "课程表接口")
public class WccCourseController extends BaseController {
	
	private final IWccCourseService iWccCourseService;
	private final WccCourseService wccCourseService;
	
	/**
	 * 分页列表
	 *
	 * @param page   分页信息
	 * @param search 　搜索关键词
	 * @return Result
	 */
	// @PreAuth
	@Log(value = "课程表列表", exception = "课程表列表请求异常")
	@PostMapping("/page")
	@ApiOperation(value = "课程表列表", notes = "分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
			@ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
			@ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
			@ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
	})
	public Result<?> page(Page page, Map search) {
		return Result.data(iWccCourseService.listPage(page, search));
	}
	
	/**
	 * 课程表信息
	 *
	 * @param id Id
	 * @return Result
	 */
	//@PreAuth
	@Log(value = "课程表信息", exception = "课程表信息请求异常")
	@PostMapping("/get")
	@ApiOperation(value = "课程表信息", notes = "根据ID查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
	})
	public Result<?> get(@RequestParam String id) {
		return Result.data(iWccCourseService.getById(id));
	}
	
	/**
	 * 课程表设置
	 *
	 * @param wccCourse WccCourse 对象
	 * @return Result
	 */
	@PreAuth
	@Log(value = "课程表设置", exception = "课程表设置请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "课程表设置", notes = "课程表设置,支持新增或修改")
	public Result<?> set(@Valid @RequestBody WccCourse wccCourse) {
		return Result.condition(iWccCourseService.saveOrUpdate(wccCourse));
	}
	
	
	//buyCourse  queryCourseDetails --我（购买课程，能查看课程详情）
	
	/**
	 * 课程表删除
	 *
	 * @param ids id字符串，根据,号分隔
	 * @return Result
	 */
	@PreAuth
	@Log(value = "课程表删除", exception = "课程表删除请求异常")
	@PostMapping("/del")
	@ApiOperation(value = "课程表删除", notes = "课程表删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
	})
	public Result<?> del(@RequestParam String ids) {
		return Result.condition(iWccCourseService.removeByIds(CollectionUtil.stringToCollection(ids)));
	}
	
	
	//-------------------------------------------------------------------
	
	
	/**
	 * 分页列表
	 *
	 * @param wccCourseForm 分页信息
	 * @return Result
	 */
	@Log(value = "获取课程列表", exception = "课程列表请求异常")
	@PostMapping(value = "/listWccCourses", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取课程列表", notes = "分页查询")
	public ResultMap<?> listWccCourses(WccCourseForm wccCourseForm) {
		try {
			LoginUser userInfo = SecurityUtil.getUsername(req);
			wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PageMap<WccCourseDto> wccCourseDtoPageMap = wccCourseService.listWccCourses(wccCourseForm);
		return ResultMap.of(ResultMap.SUCCESS_CODE, wccCourseDtoPageMap);
	}
	
	@PreAuth
	@Log(value = "获取收藏的课程", exception = "获取收藏的课程请求异常")
	@PostMapping(value = "/listWccCoursesFavorites", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取收藏的课程", notes = "分页查询")
	public ResultMap<?> listWccCoursesFavorites(WccCourseForm wccCourseForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccCourseService.listWccCoursesFavorites(wccCourseForm);
		return resultMap;
	}
	
	@PreAuth
	@Log(value = "获取已购买的课程", exception = "获取已购买的课程请求异常")
	@PostMapping(value = "/listWccCoursesBought", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取已购买的课程", notes = "分页查询")
	public ResultMap<?> listWccCoursesBought(WccCourseForm wccCourseForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccCourseService.listWccCoursesBought(wccCourseForm);
		return resultMap;
	}
	
	@Log(value = "获取课程详细信息", exception = "获取课程详细信息请求异常")
	@PostMapping(value = "/wccCourseDetail", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取课程详细信息", notes = "查询")
	public ResultMap<?> wccCourseDetail(WccCourseForm wccCourseForm) {
		try {
			LoginUser userInfo = SecurityUtil.getUsername(req);
			wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultMap resultMap = wccCourseService.wccCourseDetail(wccCourseForm);
		return resultMap;
	}
}

