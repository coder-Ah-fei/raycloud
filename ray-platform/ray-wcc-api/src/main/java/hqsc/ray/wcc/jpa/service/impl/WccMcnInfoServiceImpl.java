package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccMcnInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccMcnInfo;
import hqsc.ray.wcc.jpa.form.WccMcnInfoForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccMcnInfoRepository;
import hqsc.ray.wcc.jpa.service.WccMcnInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@AllArgsConstructor
public class WccMcnInfoServiceImpl implements WccMcnInfoService {
	
	private final WccMcnInfoRepository wccMcnInfoRepository;
	private final RaySysAttachmentRepository raySysAttachmentRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccMcnInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccMcnInfos(WccMcnInfoForm wccMcnInfoForm) {
		Specification<JpaWccMcnInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccMcnInfo> jpaWccMcnInfoList;
		Long count;
		if (wccMcnInfoForm.getPageNow() == -1) {
			jpaWccMcnInfoList = wccMcnInfoRepository.findAll(specification);
			count = Long.valueOf(jpaWccMcnInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccMcnInfoForm.getPageNow() - 1, wccMcnInfoForm.getPageSize());
			Page<JpaWccMcnInfo> wccMcnInfoPage = wccMcnInfoRepository.findAll(specification, pageable);
			jpaWccMcnInfoList = wccMcnInfoPage.getContent();
			count = wccMcnInfoPage.getTotalElements();
		}
		List<WccMcnInfoDto> list = new ArrayList<>();
		WccMcnInfoDto wccMcnInfoDto;
		for (JpaWccMcnInfo jpaWccMcnInfo : jpaWccMcnInfoList) {
			wccMcnInfoDto = new WccMcnInfoDto();
			BeanUtils.copyProperties(jpaWccMcnInfo, wccMcnInfoDto);
			wccMcnInfoDto.setIconId(jpaWccMcnInfo.getIcon() == null ? null : jpaWccMcnInfo.getIcon().getId());
			
			list.add(wccMcnInfoDto);
		}
		return new ResultMap<>(ResultMap.SUCCESS_CODE, PageMap.of(count, list));
	}
	
	/**
	 * mcn机构信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public WccMcnInfoDto findById(Long id) {
		Optional<JpaWccMcnInfo> mcnInfoOptional = wccMcnInfoRepository.findById(id);
		if (!mcnInfoOptional.isPresent()) {
			throw new RuntimeException("MCN机构不存在");
		}
		JpaWccMcnInfo jpaWccMcnInfo = mcnInfoOptional.get();
		WccMcnInfoDto wccMcnInfoDto = new WccMcnInfoDto();
		BeanUtils.copyProperties(jpaWccMcnInfo, wccMcnInfoDto);
		wccMcnInfoDto.setIconId(jpaWccMcnInfo.getIcon() == null ? null : jpaWccMcnInfo.getIcon().getId());
		return wccMcnInfoDto;
	}
	
	/**
	 * mcn机构信息设置
	 *
	 * @param wccMcnInfoForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(WccMcnInfoForm wccMcnInfoForm) {
		JpaWccMcnInfo jpaWccMcnInfo = null;
		Optional<JpaWccMcnInfo> mcnInfoOptional = wccMcnInfoRepository.findById(wccMcnInfoForm.getId() == null ? 0L : wccMcnInfoForm.getId());
		if (mcnInfoOptional.isPresent()) {
			jpaWccMcnInfo = mcnInfoOptional.get();
			BeanUtils.copyProperties(wccMcnInfoForm, jpaWccMcnInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccMcnInfoForm.getIconId() == null ? 0L : wccMcnInfoForm.getIconId());
			if (attachmentOptional.isPresent()) {
				jpaWccMcnInfo.setIcon(attachmentOptional.get());
			}
			
		} else {
			jpaWccMcnInfo = new JpaWccMcnInfo();
			BeanUtils.copyProperties(wccMcnInfoForm, jpaWccMcnInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccMcnInfoForm.getIconId() == null ? 0L : wccMcnInfoForm.getIconId());
			if (attachmentOptional.isPresent()) {
				jpaWccMcnInfo.setIcon(attachmentOptional.get());
			}
			jpaWccMcnInfo.setStatus(1);
			jpaWccMcnInfo.setIsDelete(0);
		}
		wccMcnInfoRepository.save(jpaWccMcnInfo);
		return Result.success("保存成功");
	}
	
}
