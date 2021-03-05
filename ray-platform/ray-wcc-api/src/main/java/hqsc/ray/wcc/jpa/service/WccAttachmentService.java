package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.WccAttachmentForm;

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
