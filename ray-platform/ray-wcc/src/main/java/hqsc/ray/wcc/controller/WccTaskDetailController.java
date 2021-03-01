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
import hqsc.ray.core.web.util.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import hqsc.ray.core.auth.annotation.PreAuth;
import hqsc.ray.core.log.annotation.Log;

import org.springframework.web.bind.annotation.RestController;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.service.IWccTaskDetailService;
import hqsc.ray.wcc.entity.WccTaskDetail;
import javax.validation.Valid;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.core.web.util.CollectionUtil;

import java.util.Map;

/**
 * <p>
 * 主播任务详情表 前端控制器
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-task-detail")
@Api(value = "主播任务详情表", tags = "主播任务详情表接口")
public class WccTaskDetailController extends BaseController {

    private final IWccTaskDetailService wccTaskDetailService;

    /**
     * 分页列表
     *
     * @param page   分页信息
     * @param search 　搜索关键词
     * @return Result
     */
    @PreAuth
    @Log(value = "主播任务详情表列表", exception = "主播任务详情表列表请求异常")
    @GetMapping("/page")
    @ApiOperation(value = "主播任务详情表列表", notes = "分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
        @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
        @ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
        @ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
        @ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
    })
    public Result<?> page(Page page, Map search) {
		return Result.data(wccTaskDetailService.listPage(page, search));
    }

    /**
     * 主播任务详情表信息
     *
     * @param id Id
     * @return Result
     */
    @PreAuth
    @Log(value = "主播任务详情表信息", exception = "主播任务详情表信息请求异常")
    @GetMapping("/get")
    @ApiOperation(value = "主播任务详情表信息", notes = "根据ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
    })
    public Result<?> get(@RequestParam String id) {
		return Result.data(wccTaskDetailService.getById(id));
	}

    /**
    * 主播任务详情表设置
    *
    * @param wccTaskDetail WccTaskDetail 对象
    * @return Result
    */
    @PreAuth
    @Log(value = "主播任务详情表设置", exception = "主播任务详情表设置请求异常")
    @PostMapping("/set")
    @ApiOperation(value = "主播任务详情表设置", notes = "主播任务详情表设置,支持新增或修改")
    public Result<?> set(@Valid @RequestBody WccTaskDetail wccTaskDetail) {
		return Result.condition(wccTaskDetailService.saveOrUpdate(wccTaskDetail));
	}

    /**
    * 主播任务详情表删除
    *
    * @param ids id字符串，根据,号分隔
    * @return Result
    */
    @PreAuth
    @Log(value = "主播任务详情表删除", exception = "主播任务详情表删除请求异常")
    @PostMapping("/del")
    @ApiOperation(value = "主播任务详情表删除", notes = "主播任务详情表删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
    })
    public Result<?> del(@RequestParam String ids) {
		return Result.condition(wccTaskDetailService.removeByIds(CollectionUtil.stringToCollection(ids)));
	}
}

