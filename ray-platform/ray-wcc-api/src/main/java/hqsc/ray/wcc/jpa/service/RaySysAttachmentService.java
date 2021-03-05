package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.form.RaySysAttachmentForm;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface RaySysAttachmentService {

	/**
	 * 获取数据
	 *
	 * @param raySysAttachmentForm
	 * @return ResultMap
	 */
	ResultMap listRaySysAttachments(RaySysAttachmentForm raySysAttachmentForm);

}
