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
import hqsc.ray.wcc.jpa.form.PayLogForm;
import hqsc.ray.wcc.jpa.service.OrderService;
import hqsc.ray.wcc.jpa.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信支付的controller
 *
 * @author pangu
 * @since 2020-12-30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/wcc-wechat-pay")
@Api(value = "微信支付的controller", tags = "微信支付的接口")
public class WechatPayController extends BaseController {
	
	private final OrderService orderService;
	private final PayLogService payLogService;
	
	
	/**
	 * 分页列表
	 *
	 * @param payLogForm
	 * @return Result
	 */
	@PreAuth
	@Log(value = "生成订单并获取微信支付的预支付id", exception = "生成订单并获取微信支付的预支付id请求异常")
	@PostMapping(value = "/getPrePayId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "生成订单并获取微信支付的预支付id", notes = "")
	public Result<?> getPrePayId(PayLogForm payLogForm) {
		
		LoginUser userInfo = SecurityUtil.getUsername(req);
		payLogForm.setUserId(Long.valueOf(userInfo.getUserId()));
		
		// 生成订单
//		Result<Map<String, Object>> saveOrderResult = orderService.saveOpenVipOrder(orderForm);
//		Map<String, Object> saveOrderResultData = saveOrderResult.getData();
//		Order order = (Order) saveOrderResultData.get("order");
//		OrderMember orderMember = (OrderMember) saveOrderResultData.get("orderMember");
		
		// 生成微信支付的统一下单（生成PayLog）
		return payLogService.save(payLogForm);
	}
	
	
	@Log(value = "微信支付通知", exception = "微信支付通知请求异常")
	@PostMapping(value = "/notify", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "微信支付通知", notes = "微信支付通知")
	public String notify(@RequestBody String notify) {
		try {
			orderService.getWechatPayNotifyAfter(notify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "{\n" +
				"  \"code\": \"SUCCESS\",\n" +
				"  \"message\": \"成功\"\n" +
				"}";
		
		return result;
	}
	
}

