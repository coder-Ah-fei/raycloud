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
import hqsc.ray.core.common.api.ResultCode;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.jpa.form.UserAttachmentForm;
import hqsc.ray.wcc.jpa.form.WccCourseChapterForm;
import hqsc.ray.wcc.jpa.service.UserAttachmentService;
import hqsc.ray.wcc.jpa.service.WccCourseChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 视频接口
 *
 * @author yang
 * @date 2021年3月1日
 */
@RestController
@AllArgsConstructor
@RequestMapping("/video/")
@Api(value = "视频接口", tags = "视频接口")
public class VideoController extends BaseController {
	private final UserAttachmentService userAttachmentService;
	private final WccCourseChapterService courseChapterService;
	
	/**
	 * 给视频服务器调用的接口，用来判断用户是否可以访问该资源
	 *
	 * @param userAttachmentForm
	 * @return
	 */
	@Log(value = "视频资源鉴权", exception = "视频资源鉴权请求异常")
	@RequestMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "视频资源鉴权", notes = "视频资源鉴权")
	public Result<?> authentication(@RequestHeader("X-Original-URI") String uri, UserAttachmentForm userAttachmentForm) {
		String[] split = uri.split("\\?");
		uri = split[0];
		if (uri.endsWith(".jpg") || uri.endsWith(".ts")) {
			return Result.success("恭喜你可以访问");
		}
		try {
			userAttachmentForm.setAttachmentId(Long.valueOf(req.getHeader("attachmentId")));
			LoginUser userInfo = SecurityUtil.getUsername(req);
			if (userInfo != null) {
				userAttachmentForm.setUserId(Long.valueOf(userInfo.getUserId()));
			}
			
			
			// 如果是课程资源，判断用户是否可以学习此资源
			Long courseChapterId = Long.valueOf(req.getHeader("courseChapterId"));
			if (courseChapterId != null) {
				if (userInfo == null || userInfo.getUserId() == null) {
					res.setStatus(401);
					return Result.success(ResultCode.NO_AUTHENTICATION);
				} else {
					WccCourseChapterForm courseChapterForm = new WccCourseChapterForm();
					courseChapterForm.setId(courseChapterId).setUserId(Long.valueOf(userInfo.getUserId()));
					if (!courseChapterService.canStudyCourse(courseChapterForm)) {
						res.setStatus(401);
						return Result.success(ResultCode.NO_AUTHENTICATION);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		userAttachmentService.authentication(userAttachmentForm, res);
		return Result.success("恭喜你可以访问");
	}
	
}

