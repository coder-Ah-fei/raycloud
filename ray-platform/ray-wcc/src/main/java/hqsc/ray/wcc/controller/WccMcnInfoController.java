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

import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccMcnInfoDto;
import hqsc.ray.wcc.jpa.form.WccMcnInfoForm;
import hqsc.ray.wcc.jpa.service.WccMcnInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mcn机构控制器
 *
 * @author yang
 * @date 2021年3月1日
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-mcn-info")
@Api(value = "mcn机构控制器", tags = "mcn机构控制器")
public class WccMcnInfoController extends BaseController {
	
	@Autowired
	private WccMcnInfoService wccMcnInfoService;
	
	@Log(value = "获取mcn机构列表", exception = "获取mcn机构列表请求异常")
	@PostMapping(value = "/listMcnInfos", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取mcn机构列表", notes = "获取mcn机构列表")
	public ResultMap<PageMap<WccMcnInfoDto>> listMcnInfos(WccMcnInfoForm wccMcnInfoForm) {
		ResultMap resultMap = wccMcnInfoService.listWccMcnInfos(wccMcnInfoForm);
		return resultMap;
	}
	
	@Log(value = "生成MCN榜单查询条件的内容", exception = "生成MCN榜单查询条件的内容请求异常")
	@PostMapping(value = "/findMcnSearchData", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "生成MCN榜单查询条件的内容", notes = "生成MCN榜单查询条件的内容")
	public ResultMap<PageMap<WccMcnInfoDto>> findMcnSearchData() {
		ResultMap resultMap = wccMcnInfoService.findMcnSearchData();
		return resultMap;
	}
	
	@Log(value = "根据id查找mcn机构", exception = "根据id查找mcn机构请求异常")
	@PostMapping(value = "/findMcnInfoById", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "根据id查找mcn机构", notes = "根据id查找mcn机构")
	public ResultMap<WccMcnInfoDto> findMcnInfoById(WccMcnInfoForm mcnInfoForm) {
		WccMcnInfoDto mcnInfoDto = wccMcnInfoService.findById(mcnInfoForm.getId());
		return ResultMap.success("", mcnInfoDto);
	}
	
	
}

