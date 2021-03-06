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
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccCircleInfoDto;
import hqsc.ray.wcc.jpa.form.WccCircleInfoForm;
import hqsc.ray.wcc.jpa.service.WccCircleInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 圈子信息表 前端控制器
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-circle-info/manage")
@Api(value = "圈子信息表", tags = "圈子信息表接口")
public class AdminWccCircleInfoController extends BaseController {
	
	private final WccCircleInfoService wccCircleInfoService;
	
	/**
	 * 分页列表
	 *
	 * @param wccCircleInfoForm
	 * @return Result
	 */
	@PreAuth
	@Log(value = "圈子信息表列表", exception = "圈子信息表列表请求异常")
	@GetMapping("/page")
	@ApiOperation(value = "圈子信息表列表", notes = "分页查询")
	public Result<PageMap<WccCircleInfoDto>> page(WccCircleInfoForm wccCircleInfoForm) {
		PageMap<WccCircleInfoDto> wccCircleInfoDtoPageMap = wccCircleInfoService.listWccCircleInfos(wccCircleInfoForm);
		return Result.data(wccCircleInfoDtoPageMap);
	}
	
	@PreAuth
	@Log(value = "根据id获取圈子数据", exception = "根据id获取圈子数据请求异常")
	@GetMapping("/get")
	@ApiOperation(value = "根据id获取圈子数据", notes = "根据id获取圈子数据")
	public Result<WccCircleInfoDto> get(WccCircleInfoForm wccCircleInfoForm) {
		WccCircleInfoDto wccCircleInfoDto = wccCircleInfoService.findById(wccCircleInfoForm);
		return Result.data(wccCircleInfoDto);
	}
	
	@PreAuth
	@Log(value = "保存圈子数据", exception = "保存圈子数据请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "保存圈子数据", notes = "保存圈子数据")
	public Result<?> set(@RequestBody WccCircleInfoForm wccCircleInfoForm) {
		Result result = wccCircleInfoService.save(wccCircleInfoForm);
		return result;
	}
	
}

