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
package hqsc.ray.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hqsc.ray.system.mapper.SysApiMapper;
import org.springframework.stereotype.Service;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.core.web.util.CollectionUtil;
import hqsc.ray.system.entity.SysApi;
import hqsc.ray.system.service.ISysApiService;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 系统接口表 服务实现类
 * </p>
 *
 * @author pangu
 * @since 2020-10-14
 */
@Service
public class SysApiServiceImpl extends ServiceImpl<SysApiMapper, SysApi> implements ISysApiService {

	@Override
	public IPage<SysApi> listPage(Page page, Search search, String serviceId) {
		LambdaQueryWrapper<SysApi> queryWrapper = new LambdaQueryWrapper<>();
		if (StringUtil.isNotBlank(search.getStartDate())) {
			queryWrapper.between(SysApi::getCreateTime, search.getStartDate(), search.getEndDate());
		}
		if (StringUtil.isNotBlank(search.getKeyword())) {
			queryWrapper.like(SysApi::getCode, search.getKeyword())
					.or()
					.like(SysApi::getPath, search.getKeyword());
		}
		if (!serviceId.equals("-1")) {
			queryWrapper.eq(SysApi::getServiceId, serviceId);
		}
		queryWrapper.orderByDesc(SysApi::getId);
		return this.baseMapper.selectPage(page, queryWrapper);
	}

	@Override
	public SysApi getByCode(String code) {
		LambdaQueryWrapper<SysApi> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysApi::getCode, code);
		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public boolean status(String ids, String status) {
		Collection<? extends Serializable> collection = CollectionUtil.stringToCollection(ids);

		for (Object id : collection) {
			SysApi sysApi = this.baseMapper.selectById(CollectionUtil.objectToLong(id, 0L));
			sysApi.setStatus(status);
			this.baseMapper.updateById(sysApi);
		}
		return true;
	}
}
