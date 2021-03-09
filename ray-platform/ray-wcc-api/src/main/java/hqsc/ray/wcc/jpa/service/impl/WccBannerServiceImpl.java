package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.common.enums.BannerPosition;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccBannerDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccBanner;
import hqsc.ray.wcc.jpa.form.WccBannerForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccBannerRepository;
import hqsc.ray.wcc.jpa.service.WccBannerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccBannerServiceImpl implements WccBannerService {
	
	@Autowired
	private WccBannerRepository wccBannerRepository;
	@Autowired
	private RaySysAttachmentRepository raySysAttachmentRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccBannerForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccBanners(WccBannerForm wccBannerForm) {
		Specification<JpaWccBanner> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
//				if (!StringUtils.empty(articleForm.getSectionName())) {
//					Join<Object, Object> section = root.join("section");
//					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
//				}
			if (wccBannerForm.getBannerPosition() != null) {
				pr.add(criteriaBuilder.equal(root.get("bannerPosition").as(BannerPosition.class), wccBannerForm.getBannerPosition()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sort")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccBanner> jpaWccBannerList;
		Long count = 0L;
		if (wccBannerForm.getPageNow() == -1) {
			jpaWccBannerList = wccBannerRepository.findAll(specification);
			count = Long.valueOf(jpaWccBannerList.size());
		} else {
			Pageable pageable = PageRequest.of(wccBannerForm.getPageNow() - 1, wccBannerForm.getPageSize());
			Page<JpaWccBanner> wccBannerPage = wccBannerRepository.findAll(specification, pageable);
			jpaWccBannerList = wccBannerPage.getContent();
			count = wccBannerPage.getTotalElements();
		}
		List<WccBannerDto> list = new ArrayList<>();
		WccBannerDto wccBannerDto;
		for (JpaWccBanner jpaWccBanner : jpaWccBannerList) {
			wccBannerDto = new WccBannerDto();
			BeanUtils.copyProperties(jpaWccBanner, wccBannerDto);
			wccBannerDto.setResourceId(jpaWccBanner.getResource().getId());
			wccBannerDto.setBannerPositionText(jpaWccBanner.getBannerPosition() == null ? "" : jpaWccBanner.getBannerPosition().getText());
			
			list.add(wccBannerDto);
		}
		return new ResultMap(ResultMap.SUCCESS_CODE, PageMap.of(count, list));
	}
	
	/**
	 * 获取banner
	 *
	 * @param id
	 * @return
	 */
	@Override
	public WccBannerDto findById(Long id) {
		Optional<JpaWccBanner> bannerOptional = wccBannerRepository.findById(id);
		if (!bannerOptional.isPresent()) {
			return null;
		}
		JpaWccBanner jpaWccBanner = bannerOptional.get();
		WccBannerDto wccBannerDto = new WccBannerDto();
		BeanUtils.copyProperties(jpaWccBanner, wccBannerDto);
		wccBannerDto.setResourceId(jpaWccBanner.getResource().getId());
		wccBannerDto.setBannerPositionText(jpaWccBanner.getBannerPosition() == null ? "" : jpaWccBanner.getBannerPosition().getText());
		return wccBannerDto;
	}
	
	/**
	 * 保存banner
	 *
	 * @param wccBannerForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(WccBannerForm wccBannerForm) {
		JpaWccBanner banner = null;
		Optional<JpaWccBanner> bannerOptional = wccBannerRepository.findById(wccBannerForm.getId() == null ? 0L : wccBannerForm.getId());
		if (bannerOptional.isPresent()) {
			banner = bannerOptional.get();
			BeanUtils.copyProperties(wccBannerForm, banner);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccBannerForm.getResourceId() == null ? 0L : wccBannerForm.getResourceId());
			if (attachmentOptional.isPresent()) {
				banner.setResource(attachmentOptional.get());
			}
		} else {
			banner = new JpaWccBanner();
			
			BeanUtils.copyProperties(wccBannerForm, banner);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccBannerForm.getResourceId() == null ? 0L : wccBannerForm.getResourceId());
			if (attachmentOptional.isPresent()) {
				banner.setResource(attachmentOptional.get());
			}
			banner.setStatus(1);
			banner.setIsDelete(0);
		}
		wccBannerRepository.save(banner);
		return Result.success("保存成功");
	}
	
}
