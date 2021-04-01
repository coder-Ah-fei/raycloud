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
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCourseChapterDto;
import hqsc.ray.wcc.jpa.form.WccCourseChapterForm;
import hqsc.ray.wcc.jpa.form.WccCourseForm;
import hqsc.ray.wcc.jpa.service.WccCourseChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@RequestMapping("/wcc-course-chapter/manage")
@Api(value = "课程章节表", tags = "课程章节表接口")
public class AdminWccCourseChapterController extends BaseController {
	
	private final WccCourseChapterService wccCourseChapterService;
	
	/**
	 * 分页列表
	 *
	 * @param courseChapterForm 分页信息
	 * @return Result
	 */
	// @PreAuth
	@Log(value = "课程表列表", exception = "课程表列表请求异常")
	@GetMapping("/page")
	@ApiOperation(value = "课程表列表", notes = "分页查询")
	public Result<PageMap<WccCourseChapterDto>> page(WccCourseChapterForm courseChapterForm) {
		PageMap<WccCourseChapterDto> pageMap = wccCourseChapterService.listWccCourseChapters(courseChapterForm);
		return Result.data(pageMap);
	}


//	/**
//	 * 根据id获取课程
//	 *
//	 * @param wccCourseForm Id
//	 * @return Result
//	 */
//	//@PreAuth
//	@Log(value = "根据id获取课程", exception = "根据id获取课程请求异常")
//	@GetMapping("/get")
//	@ApiOperation(value = "根据id获取课程", notes = "根据id获取课程")
//	public Result<WccCourseDto> get(WccCourseForm wccCourseForm) {
//		WccCourseDto wccCourseDto = wccCourseChapterService.findById(wccCourseForm);
//		return Result.data(wccCourseDto);
//	}
	
	/**
	 * 保存课程
	 *
	 * @param courseChapterForm WccCourse 对象
	 * @return Result
	 */
	@PreAuth
	@Log(value = "保存课程", exception = "保存课程请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "保存课程", notes = "保存课程,支持新增或修改")
	public Result<?> set(@Valid @RequestBody WccCourseChapterForm courseChapterForm) {
		return wccCourseChapterService.save(courseChapterForm);
	}
	
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
//		return Result.condition(iWccCourseService.removeByIds(CollectionUtil.stringToCollection(ids)));
		return null;
	}
	
	
	//-------------------------------------------------------------------
	
	
	@PreAuth
	@Log(value = "获取收藏的课程", exception = "获取收藏的课程请求异常")
	@PostMapping(value = "/listWccCoursesFavorites", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取收藏的课程", notes = "分页查询")
	public ResultMap<?> listWccCoursesFavorites(WccCourseForm wccCourseForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccCourseChapterService.listWccCoursesFavorites(wccCourseForm);
		return resultMap;
	}
	
	@PreAuth
	@Log(value = "获取已购买的课程", exception = "获取已购买的课程请求异常")
	@PostMapping(value = "/listWccCoursesBought", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取已购买的课程", notes = "分页查询")
	public ResultMap<?> listWccCoursesBought(WccCourseForm wccCourseForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccCourseChapterService.listWccCoursesBought(wccCourseForm);
		return resultMap;
	}
	
	@PreAuth
	@Log(value = "获取课程详细信息", exception = "获取课程详细信息请求异常")
	@PostMapping(value = "/wccCourseDetail", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取课程详细信息", notes = "查询")
	public ResultMap<?> wccCourseDetail(WccCourseForm wccCourseForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccCourseChapterService.wccCourseDetail(wccCourseForm);
		return resultMap;
	}
}

