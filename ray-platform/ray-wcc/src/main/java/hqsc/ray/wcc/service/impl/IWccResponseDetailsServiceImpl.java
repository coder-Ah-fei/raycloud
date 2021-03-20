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
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.wcc.entity.WccResponseDetails;
import hqsc.ray.wcc.mapper.WccResponseDetailsMapper;
import hqsc.ray.wcc.service.IWccResponseDetailsService;
import hqsc.ray.wcc.vo.WccResponseDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回答详情表 服务实现类
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
@Service
public class IWccResponseDetailsServiceImpl extends ServiceImpl<WccResponseDetailsMapper, WccResponseDetails> implements IWccResponseDetailsService {
	
	@Autowired
	private WccResponseDetailsMapper wccResponseDetailsMapper;
	
	@Override
	public IPage<WccResponseDetails> listPage(Page page, Map<String, Object> search) {
		LambdaQueryWrapper<WccResponseDetails> queryWrapper = new LambdaQueryWrapper<>();
//			if (StringUtil.isNotBlank(search.getStartDate())) {
//				queryWrapper.between(WccResponseDetails::getCreateTime, search.getStartDate(), search.getEndDate());
//			}
//			if (StringUtil.isNotBlank(search.getKeyword())) {
//				queryWrapper.like(WccResponseDetails::getId, search.getKeyword());
//			}
		//queryWrapper.orderByDesc(WccResponseDetails::getCreationDate);
		return this.baseMapper.selectPage(page, queryWrapper);
	}
	
	@Override
	public List<WccResponseDetails> getTopicDetails(Page page, Long id) {
		QueryWrapper<WccResponseDetails> wrapper = new QueryWrapper<>();
		wrapper.eq("BELONG_ID", id);
		wrapper.eq("STATUS", 1);
		wrapper.eq("IS_DELETE", 0);
		wrapper.orderByDesc("CREATION_DATE");
		Page topicDetails = wccResponseDetailsMapper.selectPage(page, wrapper);
		return (List<WccResponseDetails>) topicDetails.getRecords();
	}
	
	@Override
	public List<WccResponseDetails> getMyResponse(Page page, Long id) {
		QueryWrapper<WccResponseDetails> wrapper = new QueryWrapper<>();
		wrapper.eq("USER_ID", id);
		wrapper.eq("STATUS", 1);
		wrapper.eq("IS_DELETE", 0);
		wrapper.orderByDesc("CREATION_DATE");
		return (List<WccResponseDetails>) wccResponseDetailsMapper.selectPage(page, wrapper).getRecords();
		
	}
	
	/**
	 * 根据发布信息的id查找评论
	 *
	 * @param parseLong
	 * @param responseDetailParentId
	 * @return
	 */
	@Override
	public List<WccResponseDetailsVO> listResponseDetails(Long parseLong, Long responseDetailParentId) {
		List<WccResponseDetailsVO> list = wccResponseDetailsMapper.listResponseDetails(parseLong, responseDetailParentId);
		for (WccResponseDetailsVO wccResponseDetailsVO : list) {
			
			wccResponseDetailsVO.setUserNickname(StringUtil.toUnicode(wccResponseDetailsVO.getUserNickname() == null ? "" : wccResponseDetailsVO.getUserNickname()));
			
			
			if (wccResponseDetailsVO.getChildNum() > 0) {
				wccResponseDetailsVO.setChildList(listResponseDetails(parseLong, wccResponseDetailsVO.getResponseDetailId()));
			}
		}
		return list;
	}
	
	
}
