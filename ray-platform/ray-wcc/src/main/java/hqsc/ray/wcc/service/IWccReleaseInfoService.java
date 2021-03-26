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
package hqsc.ray.wcc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hqsc.ray.wcc.entity.WccReleaseInfo;
import hqsc.ray.wcc.form.WccReleaseInfoForm;
import hqsc.ray.wcc.vo.IndexReferralsVO;
import hqsc.ray.wcc.vo.IndexVideoVO;
import hqsc.ray.wcc.vo.MyReleaseInfoVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 发布信息表 服务类
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@Transactional
public interface IWccReleaseInfoService extends IService<WccReleaseInfo> {
	
	/**
	 * 分页业务方法
	 *
	 * @param page   　分页参数
	 * @param search 　搜索参数
	 * @return IPage
	 */
	IPage<WccReleaseInfo> listPage(Page page, Map<String, Object> search);
	
	List<WccReleaseInfo> getlistbyrand10(String userid);
	
	List<WccReleaseInfo> getReleaselistbyuserid(String userid);
	
	List<IndexReferralsVO> findIndexReferrals(String userId, Long current, Long size);
	
	List<IndexVideoVO> findIndexVideo(Long current, Long size);
	
	List<MyReleaseInfoVO> findMyReleaseInfo(WccReleaseInfoForm wccReleaseInfoForm, Long current, Long size);
	
	/**
	 * 我的页面，获取用户发布的内容
	 *
	 * @param page
	 * @param wccReleaseInfoForm
	 * @return
	 */
	IPage<WccReleaseInfo> listWccReleaseInfos(Page page, WccReleaseInfoForm wccReleaseInfoForm);
	
	/**
	 * 根据id 查找
	 *
	 * @param releaseInfoId
	 * @return
	 */
	MyReleaseInfoVO findById(String releaseInfoId);
	
	/**
	 * 获取我最新评论过的发布内容，并且按照最近评论的时间排序
	 *
	 * @param current
	 * @param size
	 * @param parseLong
	 * @return
	 */
	List<MyReleaseInfoVO> listWccReleaseInfosForNewestComment(long current, long size, long parseLong, Integer type);
	
	/**
	 * 查找我收藏的内容（文章，话题，视频）
	 *
	 * @param wccReleaseInfoForm
	 * @param current
	 * @param size
	 * @return
	 */
	List<MyReleaseInfoVO> getMyFavorite(WccReleaseInfoForm wccReleaseInfoForm, Long current, Long size);
	
	/**
	 * 获取我关注的用户的动态
	 *
	 * @param wccReleaseInfoForm
	 * @param current
	 * @param size
	 * @return
	 */
	List<IndexReferralsVO> listMyConcernReleaseInfos(WccReleaseInfoForm wccReleaseInfoForm, Long current, Long size);
}
