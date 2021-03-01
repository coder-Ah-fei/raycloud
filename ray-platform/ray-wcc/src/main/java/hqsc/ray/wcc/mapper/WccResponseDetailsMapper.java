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

import hqsc.ray.wcc.entity.WccResponseDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hqsc.ray.wcc.vo.WccResponseDetailsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 回答详情表 Mapper 接口
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
public interface WccResponseDetailsMapper extends BaseMapper<WccResponseDetails> {
	
	/**
	 * 根据发布信息的id查找评论
	 *
	 * @param parseLong
	 * @return
	 */
	List<WccResponseDetailsVO> listResponseDetails(@Param("parseLong") Long parseLong, @Param("responseDetailParentId") Long responseDetailParentId);
}
