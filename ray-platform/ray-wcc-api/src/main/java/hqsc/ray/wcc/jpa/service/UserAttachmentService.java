package hqsc.ray.wcc.jpa.service;

import hqsc.ray.wcc.jpa.form.UserAttachmentForm;

import javax.servlet.http.HttpServletResponse;

/**
 * 描述：
 *
 * @author Administrator
 */
public interface UserAttachmentService {
	
	/**
	 * 视频资源鉴权
	 *
	 * @param userAttachmentForm
	 */
	void authentication(UserAttachmentForm userAttachmentForm, HttpServletResponse response);
}
