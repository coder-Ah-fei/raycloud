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

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.wcc.entity.MCNOrganizationInfo;
import hqsc.ray.wcc.entity.WccCelebrityInfo;
import hqsc.ray.wcc.entity.WccOrganizationDetail;
import hqsc.ray.wcc.mapper.WccCelebrityInfoMapper;
import hqsc.ray.wcc.mapper.WccOrganizationDetailMapper;
import hqsc.ray.wcc.service.IWccOrganizationDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hqsc.ray.wcc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * <p>
 * 机构详情表 服务实现类
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@Service
public class WccOrganizationDetailServiceImpl extends ServiceImpl<WccOrganizationDetailMapper,WccOrganizationDetail> implements IWccOrganizationDetailService {


		@Autowired
		private  WccOrganizationDetailMapper wccOrganizationDetailMapper;
		@Autowired
		private WccCelebrityInfoMapper wccCelebrityInfoMapper;

		@Override
		public IPage<WccOrganizationDetail> listPage(Page page, Map<String, Object> search) {
			LambdaQueryWrapper<WccOrganizationDetail> queryWrapper = new LambdaQueryWrapper<>();
//			if (StringUtil.isNotBlank(search.getStartDate())) {
//				queryWrapper.between(WccOrganizationDetail::getCreateTime, search.getStartDate(), search.getEndDate());
//			}
//			if (StringUtil.isNotBlank(search.getKeyword())) {
//				queryWrapper.like(WccOrganizationDetail::getId, search.getKeyword());
//			}
//			//queryWrapper.orderByDesc(WccOrganizationDetail::getCreationDate);
			queryWrapper.eq(!ObjectUtil.isEmpty(search.get("TYPE")),WccOrganizationDetail::getType,search.get("TYPE"));
			queryWrapper.eq(WccOrganizationDetail::getStatus ,1);
			queryWrapper.eq(WccOrganizationDetail::getIsDelete ,0);
			queryWrapper.orderByDesc();
			return this.baseMapper.selectPage(page, queryWrapper);
		}

		@Override
		public List<WccOrganizationDetail> listPageByAddress(Page page, Map<String, Object> search) {
			LambdaQueryWrapper<WccOrganizationDetail> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ObjectUtil.isNotEmpty(search.get("ADDRESS")),WccOrganizationDetail::getAddress,search.get("ADDRESS"));
			queryWrapper.eq(WccOrganizationDetail::getStatus ,1);
			queryWrapper.eq(WccOrganizationDetail::getIsDelete ,0);
			return this.baseMapper.selectPage(page, queryWrapper).getRecords();
		}

	@Override
	public List<WccOrganizationDetail> listPageByType(Page page, Map<String, Object> search) {
		LambdaQueryWrapper<WccOrganizationDetail> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ObjectUtil.isNotEmpty(search.get("TYPE")),WccOrganizationDetail::getType,search.get("TYPE"));
		queryWrapper.eq(WccOrganizationDetail::getStatus ,1);
		queryWrapper.eq(WccOrganizationDetail::getIsDelete ,0);
//			queryWrapper.orderByDesc();
		return this.baseMapper.selectPage(page, queryWrapper).getRecords();
	}

	@Override
	public List<WccOrganizationDetail> listPageByField(Page page, Map<String, Object> search) {
		LambdaQueryWrapper<WccOrganizationDetail> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ObjectUtil.isNotEmpty(search.get("FIELD")),WccOrganizationDetail::getField,search.get("FIELD"));
		queryWrapper.eq(WccOrganizationDetail::getStatus ,1);
		queryWrapper.eq(WccOrganizationDetail::getIsDelete ,0);
//			queryWrapper.orderByDesc();
		return this.baseMapper.selectPage(page, queryWrapper).getRecords();
	}




}
