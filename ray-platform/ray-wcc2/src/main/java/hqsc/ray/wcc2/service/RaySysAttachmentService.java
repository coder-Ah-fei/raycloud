package hqsc.ray.wcc2.service;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.form.RaySysAttachmentForm;

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
