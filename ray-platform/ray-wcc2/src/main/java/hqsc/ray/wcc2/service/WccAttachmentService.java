package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.WccAttachmentForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface WccAttachmentService {

	/**
	 * 获取数据
	 *
	 * @param wccAttachmentForm
	 * @return ResultMap
	 */
	ResultMap listWccAttachments(WccAttachmentForm wccAttachmentForm);

}
