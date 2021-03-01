package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccMoneyReceiptsForm;

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
