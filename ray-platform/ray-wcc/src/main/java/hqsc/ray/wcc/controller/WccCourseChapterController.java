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
import hqsc.ray.wcc.jpa.dto.WccCourseChapterDto;
import hqsc.ray.wcc.jpa.form.WccCourseChapterForm;
import hqsc.ray.wcc.jpa.service.WccCourseChapterService;
import hqsc.ray.wcc.service.IWccCourseService;
import hqsc.ray.wcc.service.IWccResponseDetailsService;
import hqsc.ray.wcc.vo.WccResponseDetailsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/wcc-course-chapter")
@Api(value = "课程章节表", tags = "课程章节表接口")
public class WccCourseChapterController extends BaseController {
	
	private final IWccCourseService iWccCourseService;
	private final WccCourseChapterService courseChapterService;
	private final IWccResponseDetailsService iWccResponseDetailsService;
	
	@Log(value = "根据id获取课程章节详细信息", exception = "根据id获取课程章节详细信息请求异常")
	@PostMapping(value = "/findCourseChapterById", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "根据id获取课程章节详细信息", notes = "根据id获取课程章节详细信息")
	public Result<?> findCourseChapterById(WccCourseChapterForm courseChapterForm) {
		try {
			LoginUser userInfo = SecurityUtil.getUsername(req);
			courseChapterForm.setUserId(Long.valueOf(userInfo.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		WccCourseChapterDto courseChapterDto = courseChapterService.findById(courseChapterForm);
		List<WccResponseDetailsVO> responseDetails = iWccResponseDetailsService.listResponseDetails(courseChapterDto.getId(), 2, null);
		Map<String, Object> map = new HashMap<>(5);
		map.put("courseChapter", courseChapterDto);
		map.put("responseDetails", responseDetails);
		return Result.data(map);
	}
}

