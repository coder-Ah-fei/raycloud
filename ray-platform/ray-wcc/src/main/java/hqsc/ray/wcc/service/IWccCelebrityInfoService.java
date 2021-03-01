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
import hqsc.ray.wcc.entity.MCNOrganizationInfo;
import hqsc.ray.wcc.entity.WccCelebrityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;
/**
 * <p>
 * 红人信息表 服务类
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
public interface IWccCelebrityInfoService extends IService<WccCelebrityInfo> {

	/**
     * 分页业务方法
     * @param page　分页参数
     * @param search　搜索参数
     * @return IPage
     */
	IPage<WccCelebrityInfo> listPage(Page page, Map<String, Object> search);

	List<MCNOrganizationInfo> findOrganizationByFansRank(Long current, Long size);

}
