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

import hqsc.ray.core.auth.annotation.PreAuth;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.log.annotation.Log;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.jpa.form.OrderForm;
import hqsc.ray.wcc.jpa.service.OrderService;
import hqsc.ray.wcc.jpa.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 订单的controller
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wcc-order")
@Api(value = "订单的controller", tags = "订单的接口")
public class OrderController extends BaseController {
	
	private final OrderService orderService;
	private final PayLogService payLogService;
	
	/**
	 * 生成开通vip的订单
	 *
	 * @param orderForm
	 * @return Result
	 */
	@PreAuth
	@Log(value = "生成开通vip的订单", exception = "生成开通vip的订单请求异常")
	@PostMapping(value = "/createOpenVipOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "生成开通vip的订单", notes = "")
	public Result<?> getPrePayId(OrderForm orderForm) {
		LoginUser userInfo = SecurityUtil.getUsername(req);
		orderForm.setJpaWccUserId(Long.valueOf(userInfo.getUserId()));
		// 生成订单
		Result<Map<String, Object>> saveOrderResult = orderService.saveOpenVipOrder(orderForm);
		return saveOrderResult;
	}
	
}

