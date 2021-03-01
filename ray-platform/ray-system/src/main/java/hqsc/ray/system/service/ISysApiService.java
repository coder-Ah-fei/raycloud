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
package hqsc.ray.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.system.entity.SysApi;

/**
 * <p>
 * 系统接口表 服务类
 * </p>
 *
 * @author pangu
 * @since 2020-10-14
 */
public interface ISysApiService extends IService<SysApi> {

	/**
	 * 分页业务方法
	 *
	 * @param page   　分页参数
	 * @param search 　搜索参数
	 * @return IPage
	 */
	IPage<SysApi> listPage(Page page, Search search, String serviceId);

	/**
	 * 根据code查询对象
	 *
	 * @param code 编码
	 * @return SysApi
	 */
	SysApi getByCode(String code);


	/**
	 * 设置API状态
	 *
	 * @param ids    id串
	 * @param status 　状态标识
	 * @return Boolean
	 */
	boolean status(String ids, String status);
}
