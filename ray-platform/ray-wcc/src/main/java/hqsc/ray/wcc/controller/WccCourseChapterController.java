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

import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCourseChapterDto;
import hqsc.ray.wcc.jpa.form.WccCourseChapterForm;
import hqsc.ray.wcc.jpa.form.WccCourseForm;
import hqsc.ray.wcc.jpa.service.WccCourseChapterService;
import hqsc.ray.wcc.jpa.service.WccCourseService;
import hqsc.ray.wcc.service.IWccCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/wcc-course-chapter")
@Api(value = "课程章节表", tags = "课程章节表接口")
public class WccCourseChapterController extends BaseController {
	
	private final IWccCourseService iWccCourseService;
	private final WccCourseChapterService courseChapterService;
	
	
	@Log(value = "根据id获取课程章节详细信息", exception = "根据id获取课程章节详细信息请求异常")
	@PostMapping(value = "/findCourseChapterById", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "根据id获取课程章节详细信息", notes = "根据id获取课程章节详细信息")
	public Result<WccCourseChapterDto> findCourseChapterById(WccCourseChapterForm courseChapterForm) {
		try {
			LoginUser userInfo = SecurityUtil.getUsername(req);
//			wccCourseForm.setUserId(Long.valueOf(userInfo.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Result<WccCourseChapterDto> result = courseChapterService.findById(courseChapterForm);
		return result;
	}
}

