package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.util.DateUtil;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccResponseDetailsDto;
import hqsc.ray.wcc.jpa.entity.JpaWccReleaseInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccResponseDetails;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.entity.JpaWccUserMessage;
import hqsc.ray.wcc.jpa.form.WccResponseDetailsForm;
import hqsc.ray.wcc.jpa.repository.WccReleaseInfoRepository;
import hqsc.ray.wcc.jpa.repository.WccResponseDetailsRepository;
import hqsc.ray.wcc.jpa.repository.WccUserMessageRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.WccResponseDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccResponseDetailsServiceImpl implements WccResponseDetailsService {
	
	@Autowired
	private WccResponseDetailsRepository wccResponseDetailsRepository;
	@Autowired
	private WccUserRepository wccUserRepository;
	@Autowired
	private WccReleaseInfoRepository wccReleaseInfoRepository;
	@Autowired
	private WccUserMessageRepository wccUserMessageRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccResponseDetailsForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccResponseDetailss(WccResponseDetailsForm wccResponseDetailsForm) {
		Specification<JpaWccResponseDetails> specification = (root, criteriaQuery, criteriaBuilder) -> {
//			List<Predicate> pr = new ArrayList< >();
//				if (!StringUtils.empty(articleForm.getSectionName())) {
//					Join<Object, Object> section = root.join("section");
//					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
//				}

//			if (!StringUtils.empty(litigationEnvelopeBrandForm.getBrandName())) {
//				pr.add(criteriaBuilder.equal(root.get("brandName").as(String.class), litigationEnvelopeBrandForm.getBrandName()));
//			}
//			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccResponseDetails> jpaWccResponseDetailsList;
		Long count = 0L;
		if (wccResponseDetailsForm.getPageNow() == -1) {
			jpaWccResponseDetailsList = wccResponseDetailsRepository.findAll(specification);
			count = Long.valueOf(jpaWccResponseDetailsList.size());
		} else {
			Pageable pageable = PageRequest.of(wccResponseDetailsForm.getPageNow() - 1, wccResponseDetailsForm.getPageSize());
			Page<JpaWccResponseDetails> wccResponseDetailsPage = wccResponseDetailsRepository.findAll(specification, pageable);
			jpaWccResponseDetailsList = wccResponseDetailsPage.getContent();
			count = wccResponseDetailsPage.getTotalElements();
		}
		List<WccResponseDetailsDto> list = new ArrayList<>();
		WccResponseDetailsDto wccResponseDetailsDto;
		
		
		for (JpaWccResponseDetails jpaWccResponseDetails : jpaWccResponseDetailsList) {
			wccResponseDetailsDto = new WccResponseDetailsDto();
			BeanUtils.copyProperties(jpaWccResponseDetails, wccResponseDetailsDto);
			
			wccResponseDetailsDto.setUserId(jpaWccResponseDetails.getJpaWccUser().getId());
			wccResponseDetailsDto.setUserNickname(jpaWccResponseDetails.getJpaWccUser().getNickname());
			wccResponseDetailsDto.setResponseTimeStr(jpaWccResponseDetails.getResponseTime() == null ? "" : DateUtil.formatLocalDateTime(jpaWccResponseDetails.getResponseTime()));
			wccResponseDetailsDto.setReleaseInfoId(jpaWccResponseDetails.getJpaWccReleaseInfo().getId());
			wccResponseDetailsDto.setResponseDetailsId(jpaWccResponseDetails.getJpaWccResponseDetails().getId());
			
			list.add(wccResponseDetailsDto);
		}
		return new ResultMap<>(ResultMap.SUCCESS_CODE, PageMap.of(count, list));
	}
	
	/**
	 * 新增评论
	 *
	 * @param wccResponseDetailsForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultMap saveWccResponseDetails(WccResponseDetailsForm wccResponseDetailsForm) {
		
		JpaWccResponseDetails comment = new JpaWccResponseDetails();
		BeanUtils.copyProperties(wccResponseDetailsForm, comment);
		JpaWccUser jpaWccUser = wccUserRepository.findById(wccResponseDetailsForm.getUserId()).get();
		comment.setJpaWccUser(jpaWccUser);
		
		Optional<JpaWccReleaseInfo> releaseInfoOptional = wccReleaseInfoRepository.findById(wccResponseDetailsForm.getBelongId());
		if (!releaseInfoOptional.isPresent()) {
			return ResultMap.of("被评论的数据没有找到");
		}
		JpaWccReleaseInfo jpaWccReleaseInfo = releaseInfoOptional.get();
		comment.setJpaWccReleaseInfo(jpaWccReleaseInfo);
		comment.setFavoriteCount(0);
		// 查找上级评论
		if (wccResponseDetailsForm.getParentId() != null) {
			Optional<JpaWccResponseDetails> wccResponseDetailsOptional = wccResponseDetailsRepository.findById(wccResponseDetailsForm.getParentId());
			if (wccResponseDetailsOptional.isPresent()) {
				comment.setJpaWccResponseDetails(wccResponseDetailsOptional.get());
			}
		}
		comment.setResponseTime(LocalDateTime.now());
		wccResponseDetailsRepository.save(comment);
		
		
		// 新增一条消息
		JpaWccUserMessage jpaWccUserMessage = new JpaWccUserMessage();
		jpaWccUserMessage.setJpaWccUser(jpaWccReleaseInfo.getBelongUser());
		if (jpaWccReleaseInfo.getType() == 0L) {
			jpaWccUserMessage.setMessageType(1);
			jpaWccUserMessage.setMessageContent(jpaWccUser.getNickname() + "回答了你的提问");
		} else {
			jpaWccUserMessage.setMessageType(0);
			jpaWccUserMessage.setMessageContent(comment.getResponseBody());
		}
		jpaWccUserMessage.setMessageTime(new Date());
		jpaWccUserMessage.setIsRead(0);
		wccUserMessageRepository.save(jpaWccUserMessage);
		
		return ResultMap.of(ResultMap.SUCCESS_CODE);
	}
	
}
