package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccAuthCenterDto;
import hqsc.ray.wcc2.entity.RaySysAttachment;
import hqsc.ray.wcc2.entity.WccAuthCenter;
import hqsc.ray.wcc2.entity.WccUser;
import hqsc.ray.wcc2.form.WccAuthCenterForm;
import hqsc.ray.wcc2.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc2.repository.WccAuthCenterRepository;
import hqsc.ray.wcc2.repository.WccUserRepository;
import hqsc.ray.wcc2.service.WccAuthCenterService;
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
public class WccAuthCenterServiceImpl implements WccAuthCenterService {
	
	@Autowired
	private WccAuthCenterRepository wccAuthCenterRepository;
	@Autowired
	private WccUserRepository wccUserRepository;
	@Autowired
	private RaySysAttachmentRepository raySysAttachmentRepository;
	
	
	/**
	 * 获取数据
	 *
	 * @param wccAuthCenterForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccAuthCenters(WccAuthCenterForm wccAuthCenterForm) {
		Specification<WccAuthCenter> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccAuthCenter> wccAuthCenterList;
		if (wccAuthCenterForm.getPageNow() == -1) {
			wccAuthCenterList = wccAuthCenterRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccAuthCenterForm.getPageNow() - 1, wccAuthCenterForm.getPageSize());
			Page<WccAuthCenter> wccAuthCenterPage = wccAuthCenterRepository.findAll(specification, pageable);
			wccAuthCenterList = wccAuthCenterPage.getContent();
		}
		List<WccAuthCenterDto> list = new ArrayList<>();
		WccAuthCenterDto wccAuthCenterDto;
		for (WccAuthCenter wccAuthCenter : wccAuthCenterList) {
			wccAuthCenterDto = new WccAuthCenterDto();
			BeanUtils.copyProperties(wccAuthCenter, wccAuthCenterDto);
			
			
			list.add(wccAuthCenterDto);
		}
		long count = wccAuthCenterRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
	/**
	 * 主播证报名
	 *
	 * @param wccAuthCenterForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultMap anchorInfoSubmit(WccAuthCenterForm wccAuthCenterForm) {
		List<WccAuthCenter> wccAuthCenterList = wccAuthCenterRepository.findByWccUserId(wccAuthCenterForm.getUserId());
		if (wccAuthCenterList.size() > 0) {
			return ResultMap.of("您已经报名");
		}
		
		WccAuthCenter wccAuthCenter = new WccAuthCenter();
		BeanUtils.copyProperties(wccAuthCenterForm, wccAuthCenter);
		wccAuthCenter.setBusinessStatus(1);
		
		Optional<WccUser> wccUserOptional = wccUserRepository.findById(wccAuthCenterForm.getUserId());
		wccAuthCenter.setWccUser(wccUserOptional.get());
		Optional<RaySysAttachment> wccAttachmentOptional = raySysAttachmentRepository.findById(wccAuthCenterForm.getFrontId());
		if (wccAttachmentOptional.isPresent()) {
			wccAuthCenter.setFront(wccAttachmentOptional.get());
		}
		wccAttachmentOptional = raySysAttachmentRepository.findById(wccAuthCenterForm.getBackId());
		if (wccAttachmentOptional.isPresent()) {
			wccAuthCenter.setBack(wccAttachmentOptional.get());
		}
		wccAttachmentOptional = raySysAttachmentRepository.findById(wccAuthCenterForm.getIdPhotoId());
		if (wccAttachmentOptional.isPresent()) {
			wccAuthCenter.setIdPhoto(wccAttachmentOptional.get());
		}
		wccAuthCenterRepository.save(wccAuthCenter);
		return ResultMap.of(ResultMap.SUCCESS_CODE);
	}
	
	/**
	 * 判断用户是否已经主播报名成功
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public ResultMap<?> isAnchorInfoSubmit(Long userId) {
		List<WccAuthCenter> wccAuthCenterList = wccAuthCenterRepository.findByWccUserId(userId);
		if (wccAuthCenterList.size() > 0) {
			return ResultMap.of(ResultMap.SUCCESS_CODE, true);
		}
		return ResultMap.of(ResultMap.SUCCESS_CODE, false);
	}
	
}
