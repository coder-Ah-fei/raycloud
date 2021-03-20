package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccUserMessageForm;

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
	
	/**
	 * 获取详细消息列表
	 *
	 * @param wccUserMessageForm
	 * @return ResultMap
	 */
	ResultMap listWccUserMessageDetails(WccUserMessageForm wccUserMessageForm);
	
	/**
	 * 将消息设置为已读
	 *
	 * @param wccUserMessageForm
	 * @return
	 */
	ResultMap setAsRead(WccUserMessageForm wccUserMessageForm);
}
