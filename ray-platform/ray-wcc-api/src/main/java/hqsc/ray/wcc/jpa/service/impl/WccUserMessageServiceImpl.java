package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.util.DateUtil;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccReleaseInfoDto;
import hqsc.ray.wcc.jpa.dto.WccUserDto;
import hqsc.ray.wcc.jpa.dto.WccUserMessageDto;
import hqsc.ray.wcc.jpa.entity.JpaWccReleaseInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccResponseDetails;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.entity.JpaWccUserMessage;
import hqsc.ray.wcc.jpa.form.WccUserMessageForm;
import hqsc.ray.wcc.jpa.repository.WccReleaseInfoRepository;
import hqsc.ray.wcc.jpa.repository.WccUserMessageRepository;
import hqsc.ray.wcc.jpa.service.WccUserMessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
@AllArgsConstructor
public class WccUserMessageServiceImpl implements WccUserMessageService {
	
	private final WccUserMessageRepository wccUserMessageRepository;
	private final WccReleaseInfoRepository releaseInfoRepository;
	
	
	/**
	 * 获取数据
	 *
	 * @param wccUserMessageForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUserMessages(WccUserMessageForm wccUserMessageForm) {
		Specification<JpaWccUserMessage> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
			if (wccUserMessageForm.getUserId() != null) {
				Join<Object, Object> wccUser = root.join("jpaWccUser");
				pr.add(criteriaBuilder.equal(wccUser.get("id"), wccUserMessageForm.getUserId()));
			}
			if (wccUserMessageForm.getMessageType() != null) {
				pr.add(criteriaBuilder.equal(root.get("messageType").as(Integer.class), wccUserMessageForm.getMessageType()));
			}
			if (wccUserMessageForm.getIsRead() != null) {
				pr.add(criteriaBuilder.equal(root.get("isRead"), wccUserMessageForm.getIsRead()));
			}
			
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccUserMessage> jpaWccUserMessageList;
		if (wccUserMessageForm.getPageNow() == -1) {
			jpaWccUserMessageList = wccUserMessageRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserMessageForm.getPageNow() - 1, wccUserMessageForm.getPageSize());
			Page<JpaWccUserMessage> wccUserMessagePage = wccUserMessageRepository.findAll(specification, pageable);
			jpaWccUserMessageList = wccUserMessagePage.getContent();
		}
		List<WccUserMessageDto> list = new ArrayList<>();
		WccUserMessageDto wccUserMessageDto;
		for (JpaWccUserMessage jpaWccUserMessage : jpaWccUserMessageList) {
			wccUserMessageDto = new WccUserMessageDto();
			simple2Dto(jpaWccUserMessage, wccUserMessageDto);
			list.add(wccUserMessageDto);
		}
		long count = wccUserMessageRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("", map);
	}
	
	/**
	 * 获取详细消息列表
	 *
	 * @param wccUserMessageForm
	 * @return
	 */
	@Override
	public ResultMap listWccUserMessageDetails(WccUserMessageForm wccUserMessageForm) {
		Specification<JpaWccUserMessage> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
			if (wccUserMessageForm.getUserId() != null) {
				Join<Object, Object> wccUser = root.join("jpaWccUser");
				pr.add(criteriaBuilder.equal(wccUser.get("id"), wccUserMessageForm.getUserId()));
			}
			if (wccUserMessageForm.getMessageType() != null) {
				pr.add(criteriaBuilder.equal(root.get("messageType").as(Integer.class), wccUserMessageForm.getMessageType()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccUserMessage> jpaWccUserMessageList;
		if (wccUserMessageForm.getPageNow() == -1) {
			jpaWccUserMessageList = wccUserMessageRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserMessageForm.getPageNow() - 1, wccUserMessageForm.getPageSize());
			Page<JpaWccUserMessage> wccUserMessagePage = wccUserMessageRepository.findAll(specification, pageable);
			jpaWccUserMessageList = wccUserMessagePage.getContent();
		}
		List<WccUserMessageDto> list = new ArrayList<>();
		WccUserMessageDto wccUserMessageDto;
		for (JpaWccUserMessage jpaWccUserMessage : jpaWccUserMessageList) {
			wccUserMessageDto = new WccUserMessageDto();
			detail2Dto(jpaWccUserMessage, wccUserMessageDto);
			list.add(wccUserMessageDto);
		}
		long count = wccUserMessageRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
	/**
	 * 将消息设置为已读
	 *
	 * @param wccUserMessageForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultMap setAsRead(WccUserMessageForm wccUserMessageForm) {
		Optional<JpaWccUserMessage> userMessageOptional = wccUserMessageRepository.findById(wccUserMessageForm.getId());
		if (!userMessageOptional.isPresent()) {
			return ResultMap.of("消息不存在");
		}
		JpaWccUserMessage userMessage = userMessageOptional.get();
		if (userMessage.getIsRead() == 1) {
			return ResultMap.success("消息已经设置为已读");
		}
		userMessage.setIsRead(1);
		wccUserMessageRepository.save(userMessage);
		return ResultMap.of(ResultMap.SUCCESS_CODE);
	}
	
	/**
	 * 数据结构简单转换
	 *
	 * @return
	 */
	private void simple2Dto(JpaWccUserMessage userMessage, WccUserMessageDto wccUserMessageDto) {
		BeanUtils.copyProperties(userMessage, wccUserMessageDto);
		wccUserMessageDto.setMessageContent(userMessage.getMessageContent() == null ? "" : StringUtil.toUnicode(userMessage.getMessageContent()));
		wccUserMessageDto.setMessageTimeStr(userMessage.getMessageTime() == null ? "" : DateUtil.simpleFormat(userMessage.getMessageTime()));
	}
	
	/**
	 * 数据结构详细转换
	 *
	 * @return
	 */
	private void detail2Dto(JpaWccUserMessage userMessage, WccUserMessageDto wccUserMessageDto) {
		BeanUtils.copyProperties(userMessage, wccUserMessageDto);
		wccUserMessageDto.setMessageContent(userMessage.getMessageContent() == null ? "" : StringUtil.toUnicode(userMessage.getMessageContent()));
		wccUserMessageDto.setMessageTimeStr(userMessage.getMessageTime() == null ? "" : DateUtil.simpleFormat(userMessage.getMessageTime()));
		
		// 消息发表人
		JpaWccUser wccUser = userMessage.getJpaWccUser();
		if (wccUser != null) {
			WccUserDto wccUserDto = new WccUserDto();
			wccUserDto.setId(wccUser.getId());
			wccUserDto.setNickname(wccUser.getNickname());
			wccUserDto.setWechatHeadPortraitAddress(wccUser.getWechatHeadPortraitAddress());
			wccUserMessageDto.setWccUserDto(wccUserDto);
		}
		
		// 对应发布内容
		JpaWccResponseDetails responseDetails = userMessage.getResponseDetails();
		if (responseDetails != null) {
			
			Optional<JpaWccReleaseInfo> releaseInfoOptional = releaseInfoRepository.findById(responseDetails.getBelongId());
			
			if (releaseInfoOptional.isPresent()) {
				JpaWccReleaseInfo releaseInfo = releaseInfoOptional.get();
				WccReleaseInfoDto releaseInfoDto = new WccReleaseInfoDto();
				BeanUtils.copyProperties(releaseInfo, releaseInfoDto);
				releaseInfoDto.setTitel(releaseInfo.getTitel());
				releaseInfoDto.setBelongUserNickname(releaseInfo.getBelongUser().getNickname());
				releaseInfoDto.setType(releaseInfo.getType());
				wccUserMessageDto.setReleaseInfoDto(releaseInfoDto);
			}
		}
	}
}
