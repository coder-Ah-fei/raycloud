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
import hqsc.ray.wcc.entity.WccResponseDetails;
import hqsc.ray.wcc.vo.WccResponseDetailsVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回答详情表 服务类
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
public interface IWccResponseDetailsService extends IService<WccResponseDetails> {
	
	/**
	 * 分页业务方法
	 *
	 * @param page   　分页参数
	 * @param search 　搜索参数
	 * @return IPage
	 */
	IPage<WccResponseDetails> listPage(Page page, Map<String, Object> search);
	
	/*
	 * @param page　分页参数
	 * @param id　话题id
	 * 根据发布信息获取回复详情
	 * */
	List<WccResponseDetails> getTopicDetails(Page page, Long id);
	
	/*
	 * @param page　分页参数
	 * @param id　用户id
	 * 根据发布信息获取回复详情
	 * */
	List<WccResponseDetails> getMyResponse(Page page, Long id);
	
	/**
	 * 根据发布信息的id查找评论/回答
	 *
	 * @param parseLong
	 * @param responseDetailParentId
	 * @return
	 */
	List<WccResponseDetailsVO> listResponseDetails(Long parseLong, Long responseDetailParentId);
}
