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
import hqsc.ray.system.mapper.SysRouteMapper;
import org.springframework.stereotype.Service;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.system.entity.SysRoute;
import hqsc.ray.system.service.ISysRouteService;
import hqsc.ray.system.vo.SysRouteVO;

import java.util.List;

/**
 * <p>
 * 系统路由表 服务实现类
 * </p>
 *
 * @author pangu
 * @since 2020-10-17
 */
@Service
public class SysRouteServiceImpl extends ServiceImpl<SysRouteMapper, SysRoute> implements ISysRouteService {

	@Override
	public IPage<SysRoute> listPage(Page page, Search search) {
		LambdaQueryWrapper<SysRoute> queryWrapper = new LambdaQueryWrapper<>();
		if (StringUtil.isNotBlank(search.getStartDate())) {
			queryWrapper.between(SysRoute::getCreateTime, search.getStartDate(), search.getEndDate());
		}
		if (StringUtil.isNotBlank(search.getKeyword())) {
			queryWrapper.like(SysRoute::getServiceId, search.getKeyword())
					.or()
					.like(SysRoute::getName, search.getKeyword());
		}
		queryWrapper.orderByDesc(SysRoute::getCreateTime);
		return this.baseMapper.selectPage(page, queryWrapper);
	}

	@Override
	public List<SysRouteVO> listItem() {
		return this.baseMapper.listItem();
	}
}
