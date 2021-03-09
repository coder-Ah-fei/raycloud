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
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccGoodsInfoDto;
import hqsc.ray.wcc.jpa.dto.WccMcnInfoDto;
import hqsc.ray.wcc.jpa.form.WccGoodsInfoForm;
import hqsc.ray.wcc.jpa.form.WccMcnInfoForm;
import hqsc.ray.wcc.jpa.service.WccMcnInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * mcn机构控制器
 *
 * @author yang
 * @date 2021年3月1日
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-mcn-info/manage")
@Api(value = "mcn机构控制器", tags = "mcn机构控制器")
public class AdminWccMcnInfoController extends BaseController {
	
	@Autowired
	private WccMcnInfoService wccMcnInfoService;
	
	@PreAuth
	@Log(value = "获取mcn机构列表", exception = "获取mcn机构列表请求异常")
	@GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取mcn机构列表", notes = "获取mcn机构列表")
	public ResultMap<PageMap<WccMcnInfoDto>> listMcnInfos(WccMcnInfoForm wccMcnInfoForm) {
		ResultMap resultMap = wccMcnInfoService.listWccMcnInfos(wccMcnInfoForm);
		return resultMap;
	}
	
	@PreAuth
	@Log(value = "mcn机构信息", exception = "mcn机构信息请求异常")
	@GetMapping("/get")
	@ApiOperation(value = "mcn机构信息", notes = "根据ID查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
	})
	public Result<WccMcnInfoDto> get(@RequestParam Long id) {
		return Result.data(wccMcnInfoService.findById(id));
	}
	
	@PreAuth
	@Log(value = "mcn机构信息设置", exception = "mcn机构信息设置请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "mcn机构信息设置", notes = "mcn机构信息设置,支持新增或修改")
	public Result<?> set(@Valid @RequestBody WccMcnInfoForm wccMcnInfoForm) {
		return wccMcnInfoService.save(wccMcnInfoForm);
	}
}

