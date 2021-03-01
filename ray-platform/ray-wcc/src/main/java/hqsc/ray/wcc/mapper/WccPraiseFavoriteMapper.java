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

import hqsc.ray.wcc.entity.WccPraiseFavorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 * 用户点赞收藏表 Mapper 接口
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
public interface WccPraiseFavoriteMapper extends BaseMapper<WccPraiseFavorite> {
	
	/**
	 * 获取用户的获赞数量
	 *
	 * @param userId
	 * @return
	 */
	Long praisedCountForUser(@Param("userId") Long userId);
}
