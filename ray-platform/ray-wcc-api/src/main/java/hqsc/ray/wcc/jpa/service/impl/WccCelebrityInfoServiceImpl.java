package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCelebrityInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccCelebrityInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccMcnInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.form.WccCelebrityInfoForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccCelebrityInfoRepository;
import hqsc.ray.wcc.jpa.repository.WccMcnInfoRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.WccCelebrityInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
@AllArgsConstructor
public class WccCelebrityInfoServiceImpl implements WccCelebrityInfoService {
	
	private final WccCelebrityInfoRepository wccCelebrityInfoRepository;
	private final RaySysAttachmentRepository raySysAttachmentRepository;
	private final WccMcnInfoRepository wccMcnInfoRepository;
	private final WccUserRepository wccUserRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccCelebrityInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccCelebrityInfos(WccCelebrityInfoForm wccCelebrityInfoForm) {
		Map<String, Object> map = new HashMap<>();
		Specification<JpaWccCelebrityInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccCelebrityInfo> jpaWccCelebrityInfoList;
		Long count = 0L;
		if (wccCelebrityInfoForm.getPageNow() == -1) {
			jpaWccCelebrityInfoList = wccCelebrityInfoRepository.findAll(specification);
			count = Long.valueOf(jpaWccCelebrityInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccCelebrityInfoForm.getPageNow() - 1, wccCelebrityInfoForm.getPageSize());
			Page<JpaWccCelebrityInfo> wccCelebrityInfoPage = wccCelebrityInfoRepository.findAll(specification, pageable);
			jpaWccCelebrityInfoList = wccCelebrityInfoPage.getContent();
			count = wccCelebrityInfoPage.getTotalElements();
		}
		List<WccCelebrityInfoDto> list = new ArrayList<>();
		WccCelebrityInfoDto wccCelebrityInfoDto;
		for (JpaWccCelebrityInfo jpaWccCelebrityInfo : jpaWccCelebrityInfoList) {
			wccCelebrityInfoDto = new WccCelebrityInfoDto();
			BeanUtils.copyProperties(jpaWccCelebrityInfo, wccCelebrityInfoDto);
			wccCelebrityInfoDto.setHeadPortraitId(jpaWccCelebrityInfo.getHeadPortrait() == null ? 0L : jpaWccCelebrityInfo.getHeadPortrait().getId());
			if (jpaWccCelebrityInfo.getJpaWccMcnInfo() != null) {
				wccCelebrityInfoDto.setMcnInfoId(jpaWccCelebrityInfo.getJpaWccMcnInfo().getId());
				wccCelebrityInfoDto.setMcnName(jpaWccCelebrityInfo.getJpaWccMcnInfo().getOname());
			}
			list.add(wccCelebrityInfoDto);
		}
		return new ResultMap<>(ResultMap.SUCCESS_CODE, PageMap.of(count, list));
	}
	
	/**
	 * 红人信息表信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public WccCelebrityInfoDto findById(Long id) {
		Optional<JpaWccCelebrityInfo> celebrityInfoOptional = wccCelebrityInfoRepository.findById(id);
		if (!celebrityInfoOptional.isPresent()) {
			throw new RuntimeException("红人不存在");
		}
		JpaWccCelebrityInfo jpaWccCelebrityInfo = celebrityInfoOptional.get();
		WccCelebrityInfoDto wccCelebrityInfoDto = new WccCelebrityInfoDto();
		BeanUtils.copyProperties(jpaWccCelebrityInfo, wccCelebrityInfoDto);
		wccCelebrityInfoDto.setHeadPortraitId(jpaWccCelebrityInfo.getHeadPortrait() == null ? 0L : jpaWccCelebrityInfo.getHeadPortrait().getId());
		if (jpaWccCelebrityInfo.getJpaWccMcnInfo() != null) {
			wccCelebrityInfoDto.setMcnInfoId(jpaWccCelebrityInfo.getJpaWccMcnInfo().getId());
			wccCelebrityInfoDto.setMcnName(jpaWccCelebrityInfo.getJpaWccMcnInfo().getOname());
		}
		return wccCelebrityInfoDto;
	}
	
	/**
	 * 红人信息表设置
	 *
	 * @param wccCelebrityInfoForm
	 * @return
	 */
	@Override
	public Result<?> save(WccCelebrityInfoForm wccCelebrityInfoForm) {
		JpaWccCelebrityInfo celebrityInfo = null;
		Optional<JpaWccCelebrityInfo> celebrityInfoOptional = wccCelebrityInfoRepository.findById(wccCelebrityInfoForm.getId() == null ? 0L : wccCelebrityInfoForm.getId());
		if (celebrityInfoOptional.isPresent()) {
			celebrityInfo = celebrityInfoOptional.get();
			BeanUtils.copyProperties(wccCelebrityInfoForm, celebrityInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccCelebrityInfoForm.getHeadPortraitId() == null ? 0L : wccCelebrityInfoForm.getHeadPortraitId());
			if (attachmentOptional.isPresent()) {
				celebrityInfo.setHeadPortrait(attachmentOptional.get());
			}
			Optional<JpaWccMcnInfo> mcnInfoOptional = wccMcnInfoRepository.findById(wccCelebrityInfoForm.getMcnInfoId() == null ? 0L : wccCelebrityInfoForm.getMcnInfoId());
			if (mcnInfoOptional.isPresent()) {
				celebrityInfo.setJpaWccMcnInfo(mcnInfoOptional.get());
			}
			Optional<JpaWccUser> userOptional = wccUserRepository.findById(wccCelebrityInfoForm.getBelongUserId() == null ? 0L : wccCelebrityInfoForm.getBelongUserId());
			if (userOptional.isPresent()) {
				celebrityInfo.setBelongUser(userOptional.get());
			}
			
			
		} else {
			celebrityInfo = new JpaWccCelebrityInfo();
			
			BeanUtils.copyProperties(wccCelebrityInfoForm, celebrityInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccCelebrityInfoForm.getHeadPortraitId() == null ? 0L : wccCelebrityInfoForm.getHeadPortraitId());
			if (attachmentOptional.isPresent()) {
				celebrityInfo.setHeadPortrait(attachmentOptional.get());
			}
			Optional<JpaWccMcnInfo> mcnInfoOptional = wccMcnInfoRepository.findById(wccCelebrityInfoForm.getMcnInfoId() == null ? 0L : wccCelebrityInfoForm.getMcnInfoId());
			if (mcnInfoOptional.isPresent()) {
				celebrityInfo.setJpaWccMcnInfo(mcnInfoOptional.get());
			}
			Optional<JpaWccUser> userOptional = wccUserRepository.findById(wccCelebrityInfoForm.getBelongUserId() == null ? 0L : wccCelebrityInfoForm.getBelongUserId());
			if (userOptional.isPresent()) {
				celebrityInfo.setBelongUser(userOptional.get());
			}
			celebrityInfo.setStatus(1);
			celebrityInfo.setIsDelete(0);
		}
		wccCelebrityInfoRepository.save(celebrityInfo);
		return Result.success("保存成功");
	}
	
}
