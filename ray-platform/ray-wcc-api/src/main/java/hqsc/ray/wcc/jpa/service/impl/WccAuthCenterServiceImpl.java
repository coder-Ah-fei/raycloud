package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccAuthCenterDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccAuthCenter;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.form.WccAuthCenterForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccAuthCenterRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.WccAuthCenterService;
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
		Specification<JpaWccAuthCenter> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccAuthCenter> jpaWccAuthCenterList;
		if (wccAuthCenterForm.getPageNow() == -1) {
			jpaWccAuthCenterList = wccAuthCenterRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccAuthCenterForm.getPageNow() - 1, wccAuthCenterForm.getPageSize());
			Page<JpaWccAuthCenter> wccAuthCenterPage = wccAuthCenterRepository.findAll(specification, pageable);
			jpaWccAuthCenterList = wccAuthCenterPage.getContent();
		}
		List<WccAuthCenterDto> list = new ArrayList<>();
		WccAuthCenterDto wccAuthCenterDto;
		for (JpaWccAuthCenter jpaWccAuthCenter : jpaWccAuthCenterList) {
			wccAuthCenterDto = new WccAuthCenterDto();
			BeanUtils.copyProperties(jpaWccAuthCenter, wccAuthCenterDto);
			
			
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
		List<JpaWccAuthCenter> jpaWccAuthCenterList = wccAuthCenterRepository.findByJpaWccUserId(wccAuthCenterForm.getUserId());
		if (jpaWccAuthCenterList.size() > 0) {
			return ResultMap.of("您已经报名");
		}
		
		JpaWccAuthCenter jpaWccAuthCenter = new JpaWccAuthCenter();
		BeanUtils.copyProperties(wccAuthCenterForm, jpaWccAuthCenter);
		jpaWccAuthCenter.setBusinessStatus(1);
		
		Optional<JpaWccUser> wccUserOptional = wccUserRepository.findById(wccAuthCenterForm.getUserId());
		jpaWccAuthCenter.setJpaWccUser(wccUserOptional.get());
		Optional<JpaSysAttachment> wccAttachmentOptional = raySysAttachmentRepository.findById(wccAuthCenterForm.getFrontId());
		if (wccAttachmentOptional.isPresent()) {
			jpaWccAuthCenter.setFront(wccAttachmentOptional.get());
		}
		wccAttachmentOptional = raySysAttachmentRepository.findById(wccAuthCenterForm.getBackId());
		if (wccAttachmentOptional.isPresent()) {
			jpaWccAuthCenter.setBack(wccAttachmentOptional.get());
		}
		wccAttachmentOptional = raySysAttachmentRepository.findById(wccAuthCenterForm.getIdPhotoId());
		if (wccAttachmentOptional.isPresent()) {
			jpaWccAuthCenter.setIdPhoto(wccAttachmentOptional.get());
		}
		wccAuthCenterRepository.save(jpaWccAuthCenter);
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
		List<JpaWccAuthCenter> jpaWccAuthCenterList = wccAuthCenterRepository.findByJpaWccUserId(userId);
		if (jpaWccAuthCenterList.size() > 0) {
			return ResultMap.of(ResultMap.SUCCESS_CODE, true);
		}
		return ResultMap.of(ResultMap.SUCCESS_CODE, false);
	}
	
}
