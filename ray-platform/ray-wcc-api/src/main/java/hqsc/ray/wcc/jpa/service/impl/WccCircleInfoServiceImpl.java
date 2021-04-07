package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccCircleInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccCircleInfo;
import hqsc.ray.wcc.jpa.form.WccCircleInfoForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccCircleInfoRepository;
import hqsc.ray.wcc.jpa.service.WccCircleInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccCircleInfoServiceImpl implements WccCircleInfoService {
	
	@Autowired
	private WccCircleInfoRepository wccCircleInfoRepository;
	@Autowired
	private RaySysAttachmentRepository raySysAttachmentRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccCircleInfoForm
	 * @return ResultMap
	 */
	@Override
	public PageMap<WccCircleInfoDto> listWccCircleInfos(WccCircleInfoForm wccCircleInfoForm) {
		Specification<JpaWccCircleInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccCircleInfo> jpaWccCircleInfoList;
		Long count;
		if (wccCircleInfoForm.getPageNow() == -1) {
			jpaWccCircleInfoList = wccCircleInfoRepository.findAll(specification);
			count = Long.valueOf(jpaWccCircleInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccCircleInfoForm.getPageNow() - 1, wccCircleInfoForm.getPageSize());
			Page<JpaWccCircleInfo> wccCircleInfoPage = wccCircleInfoRepository.findAll(specification, pageable);
			jpaWccCircleInfoList = wccCircleInfoPage.getContent();
			count = wccCircleInfoPage.getTotalElements();
		}
		List<WccCircleInfoDto> list = new ArrayList<>();
		WccCircleInfoDto wccCircleInfoDto;
		for (JpaWccCircleInfo jpaWccCircleInfo : jpaWccCircleInfoList) {
			wccCircleInfoDto = new WccCircleInfoDto();
			BeanUtils.copyProperties(jpaWccCircleInfo, wccCircleInfoDto);
			if (jpaWccCircleInfo.getCircleImg() != null) {
				wccCircleInfoDto.setCircleImgId(jpaWccCircleInfo.getCircleImg().getId());
			}
			list.add(wccCircleInfoDto);
		}
		return PageMap.of(count, list);
	}
	
	/**
	 * 根据id获取圈子数据
	 *
	 * @param wccCircleInfoForm
	 * @return
	 */
	@Override
	public WccCircleInfoDto findById(WccCircleInfoForm wccCircleInfoForm) {
		
		Optional<JpaWccCircleInfo> jpaWccCircleInfoOptional = wccCircleInfoRepository.findById(wccCircleInfoForm.getId());
		if (!jpaWccCircleInfoOptional.isPresent()) {
			throw new RuntimeException("没找到数据");
		}
		JpaWccCircleInfo jpaWccCircleInfo = jpaWccCircleInfoOptional.get();
		
		WccCircleInfoDto wccCircleInfoDto = new WccCircleInfoDto();
		BeanUtils.copyProperties(jpaWccCircleInfo, wccCircleInfoDto);
		if (jpaWccCircleInfo.getCircleImg() != null) {
			wccCircleInfoDto.setCircleImgId(jpaWccCircleInfo.getCircleImg().getId());
		}
		
		return wccCircleInfoDto;
	}
	
	/**
	 * 保存圈子数据
	 *
	 * @param wccCircleInfoForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(WccCircleInfoForm wccCircleInfoForm) {
		JpaWccCircleInfo jpaWccCircleInfo = null;
		Optional<JpaWccCircleInfo> circleInfoOptional = wccCircleInfoRepository.findById(wccCircleInfoForm.getId() == null ? 0L : wccCircleInfoForm.getId());
		Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccCircleInfoForm.getCircleImgId() == null ? 0L : wccCircleInfoForm.getCircleImgId());
		if (circleInfoOptional.isPresent()) {
			jpaWccCircleInfo = circleInfoOptional.get();
			jpaWccCircleInfo.setCircleName(wccCircleInfoForm.getCircleName());
			jpaWccCircleInfo.setCircleImg(attachmentOptional.isPresent() ? attachmentOptional.get() : null);
			jpaWccCircleInfo.setCircleSynopsis(wccCircleInfoForm.getCircleSynopsis());
			
		} else {
			jpaWccCircleInfo = new JpaWccCircleInfo();
			jpaWccCircleInfo.setCircleName(wccCircleInfoForm.getCircleName());
			jpaWccCircleInfo.setCircleImg(attachmentOptional.isPresent() ? attachmentOptional.get() : null);
			jpaWccCircleInfo.setCircleSynopsis(wccCircleInfoForm.getCircleSynopsis());
			jpaWccCircleInfo.setStatus(1);
			jpaWccCircleInfo.setIsDelete(0);
		}
		wccCircleInfoRepository.save(jpaWccCircleInfo);
		return Result.success("保存成功");
	}
	
}
