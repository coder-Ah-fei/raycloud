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

import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccBannerDto;
import hqsc.ray.wcc.jpa.form.SysAreaForm;
import hqsc.ray.wcc.jpa.service.SysAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * banner图
 *
 * @author yang
 * @date 2021年3月1日
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys-area/manage/")
@Api(value = "行政区划", tags = "行政区划")
public class AdminSysAreaController extends BaseController {
	
	@Autowired
	private SysAreaService sysAreaService;
	
	@Log(value = "获取行政区划列表", exception = "获取行政区划列表请求异常")
	@GetMapping("/page")
	@ApiOperation(value = "获取行政区划列表", notes = "获取行政区划列表")
	public Result<PageMap<WccBannerDto>> listWccBanners(SysAreaForm sysAreaForm) {
		return Result.data(sysAreaService.listSysAreas(sysAreaForm));
	}
	
	
	@Log(value = "保存banner", exception = "保存banner请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "保存banner", notes = "保存banner,支持新增或修改")
	public Result<?> set() {
		return sysAreaService.save();
	}
}

