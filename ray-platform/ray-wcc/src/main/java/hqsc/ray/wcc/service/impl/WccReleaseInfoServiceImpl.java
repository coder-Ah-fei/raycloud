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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.wcc.entity.WccReleaseInfo;
import hqsc.ray.wcc.form.WccReleaseInfoForm;
import hqsc.ray.wcc.mapper.WccReleaseInfoMapper;
import hqsc.ray.wcc.service.IWccReleaseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hqsc.ray.wcc.util.StringUtil;
import hqsc.ray.wcc.vo.IndexReferralsVO;
import hqsc.ray.wcc.vo.IndexVideoVO;
import hqsc.ray.wcc.vo.MyReleaseInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 发布信息表 服务实现类
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@Service
public class WccReleaseInfoServiceImpl extends ServiceImpl<WccReleaseInfoMapper, WccReleaseInfo> implements IWccReleaseInfoService {
	
	@Autowired
	private WccReleaseInfoMapper wccReleaseInfoMapper;
	
	@Override
	public IPage<WccReleaseInfo> listPage(Page page, Map<String, Object> search) {
		LambdaQueryWrapper<WccReleaseInfo> queryWrapper = new LambdaQueryWrapper<>();
//			if (StringUtil.isNotBlank(search.getStartDate())) {
//				queryWrapper.between(WccReleaseInfo::getCreateTime, search.getStartDate(), search.getEndDate());
//			}
//			if (StringUtil.isNotBlank(search.getKeyword())) {
//				queryWrapper.like(WccReleaseInfo::getId, search.getKeyword());
//			}
		//queryWrapper.orderByDesc(WccReleaseInfo::getCreationDate);
		return this.baseMapper.selectPage(page, queryWrapper);
	}
	
	@Override
	public List<WccReleaseInfo> getlistbyrand10(String userid) {
		
		LambdaQueryWrapper<WccReleaseInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		if (StringUtil.isNotEmpty(userid)) {
			lambdaQueryWrapper.in(WccReleaseInfo::getBelongUserId, userid);
			lambdaQueryWrapper.eq(WccReleaseInfo::getStatus, 1);
			lambdaQueryWrapper.eq(WccReleaseInfo::getIsDelete, 0);
			lambdaQueryWrapper.last(String.valueOf(10));
			
			return wccReleaseInfoMapper.selectList(lambdaQueryWrapper);
		} else {
			return null;
		}
		
	}
	
	
	@Override
	public List<WccReleaseInfo> getReleaselistbyuserid(String userid) {
		LambdaQueryWrapper<WccReleaseInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		if (StringUtil.isNotEmpty(userid)) {
			lambdaQueryWrapper.in(WccReleaseInfo::getBelongUserId, userid);
			lambdaQueryWrapper.eq(WccReleaseInfo::getStatus, 1);
			lambdaQueryWrapper.eq(WccReleaseInfo::getIsDelete, 0);
			return wccReleaseInfoMapper.selectList(lambdaQueryWrapper);
		} else {
			return getlistbyrand10(userid);
		}
	}
	
	@Override
	public List<IndexReferralsVO> findIndexReferrals(String userId, Long current, Long size) {
		current = current - 1;
		current = current * size;
		return wccReleaseInfoMapper.findIndexReferrals(userId, current, size);
	}
	
	@Override
	public List<IndexVideoVO> findIndexVideo(Long current, Long size) {
		current = current - 1;
		current = current * size;
		return wccReleaseInfoMapper.findIndexVideo(current, size);
	}
	
	@Override
	public List<MyReleaseInfoVO> findMyReleaseInfo(WccReleaseInfoForm wccReleaseInfoForm, Long current, Long size) {
		current = current - 1;
		current = current * size;
		
		String releaseInfoIdsStr = "";
		if (wccReleaseInfoForm.getReleaseInfoIds() != null) {
			for (Long releaseInfoId : wccReleaseInfoForm.getReleaseInfoIds()) {
				releaseInfoIdsStr += releaseInfoId + ",";
			}
		}
		if (releaseInfoIdsStr.endsWith(",")) {
			releaseInfoIdsStr = releaseInfoIdsStr.substring(0, releaseInfoIdsStr.length() - 1);
		}
		
		return wccReleaseInfoMapper.findMyReleaseInfo(current, size, wccReleaseInfoForm.getBelongUserId(), wccReleaseInfoForm.getType(), releaseInfoIdsStr);
	}
	
	@Override
	public IPage<WccReleaseInfo> listWccReleaseInfos(Page page, WccReleaseInfoForm wccReleaseInfoForm) {
		LambdaQueryWrapper<WccReleaseInfo> queryWrapper = new LambdaQueryWrapper<>();
		if (wccReleaseInfoForm.getType() != null) {
			queryWrapper.eq(WccReleaseInfo::getType, wccReleaseInfoForm.getType());
		}
		if (wccReleaseInfoForm.getBelongUserId() != null) {
			queryWrapper.eq(WccReleaseInfo::getBelongUserId, wccReleaseInfoForm.getBelongUserId());
		}
//			if (StringUtil.isNotBlank(search.getStartDate())) {
//				queryWrapper.between(WccReleaseInfo::getCreateTime, search.getStartDate(), search.getEndDate());
//			}
//			if (StringUtil.isNotBlank(search.getKeyword())) {
//				queryWrapper.like(WccReleaseInfo::getId, search.getKeyword());
//			}
		queryWrapper.orderByDesc(WccReleaseInfo::getCreationDate);
		return this.baseMapper.selectPage(page, queryWrapper);
	}
	
	/**
	 * 根据id 查找
	 *
	 * @param releaseInfoId
	 * @return
	 */
	@Override
	public MyReleaseInfoVO findById(String releaseInfoId) {
		
		
		return wccReleaseInfoMapper.findById(releaseInfoId);
	}
	
	/**
	 * 获取我最新评论过的发布内容，并且按照最近评论的时间排序
	 *
	 * @param current
	 * @param size
	 * @return
	 */
	@Override
	public List<MyReleaseInfoVO> listWccReleaseInfosForNewestComment(long current, long size, long userId) {
		current = current - 1;
		current = current * size;
		return wccReleaseInfoMapper.listWccReleaseInfosForNewestComment(current, size, userId);
	}
	
	/**
	 * 查找我收藏的内容（文章，话题，视频）
	 *
	 * @param wccReleaseInfoForm
	 * @param current
	 * @param size
	 * @return
	 */
	@Override
	public List<MyReleaseInfoVO> getMyFavorite(WccReleaseInfoForm wccReleaseInfoForm, Long current, Long size) {
		current = current - 1;
		current = current * size;
		return wccReleaseInfoMapper.getMyFavorite(wccReleaseInfoForm.getFavoritesUserId(), wccReleaseInfoForm.getType(), current, size);
	}
	
	/**
	 * 获取我关注的用户的动态
	 *
	 * @param wccReleaseInfoForm
	 * @param current
	 * @param size
	 * @return
	 */
	@Override
	public List<IndexReferralsVO> listMyConcernReleaseInfos(WccReleaseInfoForm wccReleaseInfoForm, Long current, Long size) {
		current = current - 1;
		current = current * size;
		
		return wccReleaseInfoMapper.listMyConcernReleaseInfos(wccReleaseInfoForm.getUserId(), current, size);
	}
	
}
