package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccResponseDetailsDto;
import hqsc.ray.wcc2.entity.WccReleaseInfo;
import hqsc.ray.wcc2.entity.WccResponseDetails;
import hqsc.ray.wcc2.entity.WccUser;
import hqsc.ray.wcc2.entity.WccUserMessage;
import hqsc.ray.wcc2.form.WccResponseDetailsForm;
import hqsc.ray.wcc2.repository.WccReleaseInfoRepository;
import hqsc.ray.wcc2.repository.WccResponseDetailsRepository;
import hqsc.ray.wcc2.repository.WccUserMessageRepository;
import hqsc.ray.wcc2.repository.WccUserRepository;
import hqsc.ray.wcc2.service.WccResponseDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
		Specification<WccResponseDetails> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccResponseDetails> wccResponseDetailsList;
		if (wccResponseDetailsForm.getPageNow() == -1) {
			wccResponseDetailsList = wccResponseDetailsRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccResponseDetailsForm.getPageNow() - 1, wccResponseDetailsForm.getPageSize());
			Page<WccResponseDetails> wccResponseDetailsPage = wccResponseDetailsRepository.findAll(specification, pageable);
			wccResponseDetailsList = wccResponseDetailsPage.getContent();
		}
		List<WccResponseDetailsDto> list = new ArrayList<>();
		WccResponseDetailsDto wccResponseDetailsDto;
		for (WccResponseDetails wccResponseDetails : wccResponseDetailsList) {
			wccResponseDetailsDto = new WccResponseDetailsDto();
			BeanUtils.copyProperties(wccResponseDetails, wccResponseDetailsDto);
			
			
			list.add(wccResponseDetailsDto);
		}
		long count = wccResponseDetailsRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
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
		
		WccResponseDetails comment = new WccResponseDetails();
		BeanUtils.copyProperties(wccResponseDetailsForm, comment);
		WccUser wccUser = wccUserRepository.findById(wccResponseDetailsForm.getUserId()).get();
		comment.setWccUser(wccUser);
		
		Optional<WccReleaseInfo> releaseInfoOptional = wccReleaseInfoRepository.findById(wccResponseDetailsForm.getBelongId());
		if (!releaseInfoOptional.isPresent()) {
			return ResultMap.of("被评论的数据没有找到");
		}
		WccReleaseInfo wccReleaseInfo = releaseInfoOptional.get();
		comment.setWccReleaseInfo(wccReleaseInfo);
		comment.setFavoriteCount(0);
		// 查找上级评论
		if (wccResponseDetailsForm.getParentId() != null) {
			Optional<WccResponseDetails> wccResponseDetailsOptional = wccResponseDetailsRepository.findById(wccResponseDetailsForm.getParentId());
			if (wccResponseDetailsOptional.isPresent()) {
				comment.setWccResponseDetails(wccResponseDetailsOptional.get());
			}
		}
		comment.setResponseTime(new Date());
		wccResponseDetailsRepository.save(comment);
		
		
		// 新增一条消息
		WccUserMessage wccUserMessage = new WccUserMessage();
		wccUserMessage.setWccUser(wccReleaseInfo.getBelongUser());
		if (wccReleaseInfo.getType() == 0L) {
			wccUserMessage.setMessageType(1);
			wccUserMessage.setMessageContent(wccUser.getNickname() + "回答了你的提问");
		} else {
			wccUserMessage.setMessageType(0);
			wccUserMessage.setMessageContent(comment.getResponseBody());
		}
		wccUserMessage.setMessageTime(new Date());
		wccUserMessage.setIsRead(0);
		wccUserMessageRepository.save(wccUserMessage);
		
		return ResultMap.of(ResultMap.SUCCESS_CODE);
	}
	
}
