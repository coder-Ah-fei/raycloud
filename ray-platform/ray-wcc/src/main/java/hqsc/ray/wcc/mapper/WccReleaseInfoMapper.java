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
package hqsc.ray.wcc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hqsc.ray.wcc.entity.WccReleaseInfo;
import hqsc.ray.wcc.vo.IndexReferralsVO;
import hqsc.ray.wcc.vo.IndexVideoVO;
import hqsc.ray.wcc.vo.MyReleaseInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 发布信息表 Mapper 接口
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
public interface WccReleaseInfoMapper extends BaseMapper<WccReleaseInfo> {
	
	
	List<IndexReferralsVO> findIndexReferrals(@Param("userId") String userId, @Param("current") Long current, @Param("size") Long size);
	
	List<IndexVideoVO> findIndexVideo(@Param("current") Long current, @Param("size") Long size);
	
	List<MyReleaseInfoVO> findMyReleaseInfo(@Param("current") Long current, @Param("size") Long size, @Param("userId") Long userId, @Param("type") Integer type, @Param("releaseInfoIdsStr") String releaseInfoIdsStr);
	
	/**
	 * 根据id查找
	 *
	 * @param releaseInfoId
	 * @return
	 */
	MyReleaseInfoVO findById(@Param("releaseInfoId") String releaseInfoId);
	
	/**
	 * 获取我最新评论过的发布内容，并且按照最近评论的时间排序
	 *
	 * @param current
	 * @param size
	 * @param userId
	 * @return
	 */
	List<MyReleaseInfoVO> listWccReleaseInfosForNewestComment(long current, long size, long userId);
	
	/**
	 * 查找我收藏的内容（文章，话题，视频）
	 *
	 * @param favoritesUserId
	 * @param type
	 * @param current
	 * @param size
	 * @return
	 */
	List<MyReleaseInfoVO> getMyFavorite(@Param("favoritesUserId") Long favoritesUserId, @Param("type") Integer type, @Param("current") Long current, @Param("size") Long size);
	
	List<IndexReferralsVO> listMyConcernReleaseInfos(@Param("userId") Long userId, @Param("current") Long current, @Param("size") Long size);
}
