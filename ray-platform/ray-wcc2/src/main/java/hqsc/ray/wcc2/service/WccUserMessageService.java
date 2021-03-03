package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccUserMessageForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccUserMessageService {
	
	/**
	 * 获取数据
	 *
	 * @param wccUserMessageForm
	 * @return ResultMap
	 */
	ResultMap listWccUserMessages(WccUserMessageForm wccUserMessageForm);
	
}
