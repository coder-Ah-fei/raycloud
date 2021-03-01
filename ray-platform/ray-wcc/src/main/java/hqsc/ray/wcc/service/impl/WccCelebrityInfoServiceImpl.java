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
package hqsc.ray.wcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.wcc.entity.MCNOrganizationInfo;
import hqsc.ray.wcc.entity.WccCelebrityInfo;
import hqsc.ray.wcc.entity.WccOrganizationDetail;
import hqsc.ray.wcc.mapper.WccCelebrityInfoMapper;
import hqsc.ray.wcc.mapper.WccOrganizationDetailMapper;
import hqsc.ray.wcc.service.IWccCelebrityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * <p>
 * 红人信息表 服务实现类
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@Service
public class WccCelebrityInfoServiceImpl extends ServiceImpl<WccCelebrityInfoMapper, WccCelebrityInfo> implements IWccCelebrityInfoService {

	@Autowired
	private WccCelebrityInfoMapper wccCelebrityInfoMapper;
	@Autowired
	private WccOrganizationDetailMapper wccOrganizationDetailMapper;

		@Override
		public IPage<WccCelebrityInfo> listPage(Page page, Map<String, Object> search) {
			LambdaQueryWrapper<WccCelebrityInfo> queryWrapper = new LambdaQueryWrapper<>();
//			if (StringUtil.isNotBlank(search.getStartDate())) {
//				queryWrapper.between(WccCelebrityInfo::getCreateTime, search.getStartDate(), search.getEndDate());
//			}
//			if (StringUtil.isNotBlank(search.getKeyword())) {
//				queryWrapper.like(WccCelebrityInfo::getId, search.getKeyword());
//			}
			//queryWrapper.orderByDesc(WccCelebrityInfo::getCreationDate);
			return this.baseMapper.selectPage(page, queryWrapper);
		}

	@Override
	public List<MCNOrganizationInfo> findOrganizationByFansRank(Long current, Long size) {
		current = current - 1;
		current = current * size;
		return wccCelebrityInfoMapper.findOrganizationByFansRank(current,size);
	}

}
