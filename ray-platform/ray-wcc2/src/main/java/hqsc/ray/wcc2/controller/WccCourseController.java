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
import hqsc.ray.wcc2.form.WccCourseForm;
import hqsc.ray.wcc2.service.WccCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程控制器
 *
 * @author yang
 * @date 2021年3月1日
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-course")
@Api(value = "课程接口", tags = "课程接口")
public class WccCourseController extends BaseController {
	
	@Autowired
	private WccCourseService wccCourseService;
	
	/**
	 * 分页列表
	 *
	 * @param wccCourseForm 分页信息
	 * @return Result
	 */
	@PreAuth
	@Log(value = "获取课程列表", exception = "课程列表请求异常")
	@PostMapping(value = "/listWccCourses", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取课程列表", notes = "分页查询")
	public ResultMap<?> listWccCourses(WccCourseForm wccCourseForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccCourseService.listWccCourses(wccCourseForm);
		return resultMap;
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
	
	@PreAuth
	@Log(value = "获取课程详细信息", exception = "获取课程详细信息请求异常")
	@PostMapping(value = "/wccCourseDetail", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取课程详细信息", notes = "查询")
	public ResultMap<?> wccCourseDetail(WccCourseForm wccCourseForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		ResultMap resultMap = wccCourseService.wccCourseDetail(wccCourseForm);
		return resultMap;
	}
	
}

