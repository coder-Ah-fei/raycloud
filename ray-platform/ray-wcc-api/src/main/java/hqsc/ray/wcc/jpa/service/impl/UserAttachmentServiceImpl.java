package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.UserAttachment;
import hqsc.ray.wcc.jpa.form.UserAttachmentForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.UserAttachmentRepository;
import hqsc.ray.wcc.jpa.service.UserAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * 描述：
 *
 * @author Administrator
 */
@Slf4j
@Service
public class UserAttachmentServiceImpl implements UserAttachmentService {
	@Autowired
	private RaySysAttachmentRepository attachmentRepository;
	@Autowired
	private UserAttachmentRepository userAttachmentRepository;
	
	/**
	 * 视频资源鉴权
	 *
	 * @param userAttachmentForm
	 * @param response
	 */
	@Override
	public void authentication(UserAttachmentForm userAttachmentForm, HttpServletResponse response) {
		if (userAttachmentForm.getAttachmentId() == null) {
			log.info("视频来鉴权，但是视频资源不存在1");
			response.setStatus(401);
			return;
		}
		Optional<JpaSysAttachment> attachmentOptional = attachmentRepository.findById(userAttachmentForm.getAttachmentId());
		if (!attachmentOptional.isPresent()) {
			log.info("视频来鉴权，但是视频资源不存在2");
			response.setStatus(401);
			return;
		}
		JpaSysAttachment jpaSysAttachment = attachmentOptional.get();
		// 不需要鉴权
		if (jpaSysAttachment.getAuthority() == null || !jpaSysAttachment.getAuthority()) {
			response.setStatus(200);
			return;
		}
		// 需要鉴权
		if (userAttachmentForm.getUserId() == null) {
			log.info("视频来鉴权，-->需要权限，但是没有登录");
			response.setStatus(401);
			return;
		}
		List<UserAttachment> userAttachmentList = userAttachmentRepository.findByJpaWccUserIdAndSysAttachmentIdAndCanAccess(userAttachmentForm.getUserId(), jpaSysAttachment.getId(), true);
		if (userAttachmentList.size() == 0) {
			log.info("视频来鉴权，-->需要权限，但是没有权限");
			response.setStatus(401);
			return;
		}
		// 鉴权通过
		response.setStatus(200);
		return;
		
	}
}
