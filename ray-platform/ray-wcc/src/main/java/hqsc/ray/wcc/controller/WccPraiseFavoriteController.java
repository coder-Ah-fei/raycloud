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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.core.auth.annotation.PreAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.entity.WccPraiseFavorite;
import hqsc.ray.wcc.jpa.form.WccPraiseFavoriteForm;
import hqsc.ray.wcc.jpa.service.WccPraiseFavoriteService;
import hqsc.ray.wcc.service.IWccPraiseFavoriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 用户点赞收藏表 前端控制器
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-praise-favorite")
@Api(value = "用户点赞收藏表", tags = "用户点赞收藏表接口")
public class WccPraiseFavoriteController extends BaseController {
	
	private final IWccPraiseFavoriteService iWccPraiseFavoriteService;
	private final WccPraiseFavoriteService praiseFavoriteService;
	
	/**
	 * 分页列表
	 *
	 * @param page   分页信息
	 * @param search 　搜索关键词
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户点赞收藏表列表", exception = "用户点赞收藏表列表请求异常")
	@GetMapping("/page")
	@ApiOperation(value = "用户点赞收藏表列表", notes = "分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
			@ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
			@ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
			@ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
			@ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
	})
	public Result<?> page(Page page, Map search) {
		return Result.data(iWccPraiseFavoriteService.listPage(page, search));
	}
	
	/**
	 * 用户点赞收藏表信息
	 *
	 * @param id Id
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户点赞收藏表信息", exception = "用户点赞收藏表信息请求异常")
	@GetMapping("/get")
	@ApiOperation(value = "用户点赞收藏表信息", notes = "根据ID查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
	})
	public Result<?> get(@RequestParam String id) {
		return Result.data(iWccPraiseFavoriteService.getById(id));
	}
	
	/**
	 * 用户点赞收藏表设置
	 *
	 * @param wccPraiseFavorite WccPraiseFavorite 对象
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户点赞收藏表设置", exception = "用户点赞收藏表设置请求异常")
	@PostMapping("/set")
	@ApiOperation(value = "用户点赞收藏表设置", notes = "用户点赞收藏表设置,支持新增或修改")
	public Result<?> set(@Valid @RequestBody WccPraiseFavorite wccPraiseFavorite) {
		return Result.condition(iWccPraiseFavoriteService.saveOrUpdate(wccPraiseFavorite));
	}
	
	/**
	 * 用户点赞/取消点赞
	 *
	 * @param wccPraiseFavoriteForm 参数
	 * @return Result
	 */
	@PreAuth
	@Log(value = "用户点赞/取消点赞", exception = "用户点赞/取消点赞请求异常")
	@PostMapping("/likeOrUnlike")
	@ApiOperation(value = "用户点赞/取消点赞", notes = "用户点赞/取消点赞")
	public Result<?> likeOrUnlike(WccPraiseFavoriteForm wccPraiseFavoriteForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		wccPraiseFavoriteForm.setUserId(Long.valueOf(userInfo.getUserId()));
		return praiseFavoriteService.likeOrUnlike(wccPraiseFavoriteForm);
	}
}

