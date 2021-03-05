package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccMoneyReceiptsForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccMoneyReceiptsService {

	/**
	 * 获取数据
	 *
	 * @param wccMoneyReceiptsForm
	 * @return ResultMap
	 */
	ResultMap listWccMoneyReceiptss(WccMoneyReceiptsForm wccMoneyReceiptsForm);

}
