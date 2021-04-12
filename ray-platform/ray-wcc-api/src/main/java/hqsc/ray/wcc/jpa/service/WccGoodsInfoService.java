package hqsc.ray.wcc.jpa.service;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccGoodsInfoDto;
import hqsc.ray.wcc.jpa.form.WccGoodsInfoForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccGoodsInfoService {
	
	/**
	 * 获取数据
	 *
	 * @param wccGoodsInfoForm
	 * @return ResultMap
	 */
	PageMap<WccGoodsInfoDto> listWccGoodsInfos(WccGoodsInfoForm wccGoodsInfoForm);
	
	
	/**
	 * 商品信息表信息
	 *
	 * @param id
	 * @return
	 */
	WccGoodsInfoDto findById(Long id);
	
	/**
	 * 商品信息表设置,支持新增或修改
	 *
	 * @param wccGoodsInfoForm
	 * @return
	 */
	Result<?> save(WccGoodsInfoForm wccGoodsInfoForm);
}
