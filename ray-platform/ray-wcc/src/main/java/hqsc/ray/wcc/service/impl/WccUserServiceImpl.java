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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hqsc.ray.wcc.entity.WccPraiseFavorite;
import hqsc.ray.wcc.entity.WccReleaseInfo;
import hqsc.ray.wcc.entity.WccUser;
import hqsc.ray.wcc.form.WccPraiseFavoriteForm;
import hqsc.ray.wcc.mapper.WccPraiseFavoriteMapper;
import hqsc.ray.wcc.mapper.WccReleaseInfoMapper;
import hqsc.ray.wcc.mapper.WccUserMapper;
import hqsc.ray.wcc.service.IWccUserService;
import hqsc.ray.wcc.vo.MyReleaseInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@Service
public class WccUserServiceImpl extends ServiceImpl<WccUserMapper, WccUser> implements IWccUserService {
	
	@Autowired
	private WccUserMapper wccUserMapper;
	@Autowired
	private WccPraiseFavoriteMapper wccPraiseFavoriteMapper;
	@Autowired
	private WccReleaseInfoMapper wccReleaseInfoMapper;
	
	
	@Override
	public IPage<WccUser> listPage(Page page, Map<String, Object> search) {
		LambdaQueryWrapper<WccUser> queryWrapper = new LambdaQueryWrapper<>();
//			if (StringUtil.isNotBlank(search.getStartDate())) {
//				queryWrapper.between(WccUser::getCreateTime, search.getStartDate(), search.getEndDate());
//			}
//			if (StringUtil.isNotBlank(search.getKeyword())) {
//				queryWrapper.like(WccUser::getId, search.getKeyword());
//			}
		//queryWrapper.orderByDesc(WccUser::getCreationDate);
		return this.baseMapper.selectPage(page, queryWrapper);
	}
	
	@Override
	public WccUser selectOne(String unionid) {
		QueryWrapper<WccUser> wrapper = new QueryWrapper<>();
		wrapper.eq("WECHAT_UNION_ID", unionid);
		wrapper.eq("STATUS", 1);
		wrapper.eq("IS_DELETE", 0);
		return wccUserMapper.selectOne(wrapper);
	}
	
	@Override
	public List<MyReleaseInfoVO> getMyFavorite(WccPraiseFavoriteForm wccPraiseFavoriteForm, Page page) {
		List<WccReleaseInfo> myFavorite = null;
		QueryWrapper<WccPraiseFavorite> wrapper = new QueryWrapper<>();
		wrapper.select("BELONG_ID");
		wrapper.eq("USER_ID", wccPraiseFavoriteForm.getUserId());
		wrapper.eq("PRAISE_FAVORITE_TYPE", wccPraiseFavoriteForm.getPraiseFavoriteType());
		wrapper.eq("TYPE", 1);
		wrapper.eq("STATUS", 1);
		wrapper.eq("IS_DELETE", 0);
		wrapper.orderByDesc("CREATION_DATE");
		//先获取收藏信息的id
		Page praiseFavoriteList = wccPraiseFavoriteMapper.selectPage(page, wrapper);
		List<WccPraiseFavorite> records = (List<WccPraiseFavorite>) praiseFavoriteList.getRecords();
		ArrayList<Long> ids = new ArrayList<>();
		for (WccPraiseFavorite wccPraiseFavorite : records) {
			ids.add(wccPraiseFavorite.getBelongId());
		}
		//再根据ids查询所收藏的信息
		long current = page.getCurrent() - 1;
		long size = current * page.getSize();
		String releaseInfoIdsStr = "";
		for (Long releaseInfoId : ids) {
			releaseInfoIdsStr += releaseInfoId + ",";
		}
		if (releaseInfoIdsStr.endsWith(",")) {
			releaseInfoIdsStr = releaseInfoIdsStr.substring(0, releaseInfoIdsStr.length() - 1);
		}
		List<MyReleaseInfoVO> myReleaseInfo = wccReleaseInfoMapper.findMyReleaseInfo(current, size, wccPraiseFavoriteForm.getUserId(), wccPraiseFavoriteForm.getType(), releaseInfoIdsStr);
		List<WccReleaseInfo> favorites = wccReleaseInfoMapper.selectBatchIds(ids);
		return myReleaseInfo;
	}
	
	
}
