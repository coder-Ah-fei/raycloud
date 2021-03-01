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
import java.util.Map;
import com.baomidou.mybatisplus.core.metadata.IPage;
import hqsc.ray.wcc.entity.WccMoneyReceipts;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.database.entity.Search;
import hqsc.ray.core.web.util.CollectionUtil;
/**
 * <p>
 * 赏金收入表 服务类
 * </p>
 *
 * @author pangu
 * @since 2020-12-30
 */
public interface IWccMoneyReceiptsService extends IService<WccMoneyReceipts> {

	/**
     * 分页业务方法
     * @param page　分页参数
     * @param search　搜索参数
     * @return IPage
     */
	IPage<WccMoneyReceipts> listPage(Page page, Map<String, Object> search);

}
