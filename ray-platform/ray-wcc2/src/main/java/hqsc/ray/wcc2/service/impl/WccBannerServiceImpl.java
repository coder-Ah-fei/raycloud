package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.common.enums.BannerPosition;
import hqsc.ray.wcc2.dto.PageMap;
import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccBannerDto;
import hqsc.ray.wcc2.entity.WccBanner;
import hqsc.ray.wcc2.form.WccBannerForm;
import hqsc.ray.wcc2.repository.WccBannerRepository;
import hqsc.ray.wcc2.service.WccBannerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccBannerServiceImpl implements WccBannerService {
	
	@Autowired
	private WccBannerRepository wccBannerRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccBannerForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccBanners(WccBannerForm wccBannerForm) {
		Specification<WccBanner> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccBanner> wccBannerList;
		Long count = 0L;
		if (wccBannerForm.getPageNow() == -1) {
			wccBannerList = wccBannerRepository.findAll(specification);
			count = Long.valueOf(wccBannerList.size());
		} else {
			Pageable pageable = PageRequest.of(wccBannerForm.getPageNow() - 1, wccBannerForm.getPageSize());
			Page<WccBanner> wccBannerPage = wccBannerRepository.findAll(specification, pageable);
			wccBannerList = wccBannerPage.getContent();
			count = wccBannerPage.getTotalElements();
		}
		List<WccBannerDto> list = new ArrayList<>();
		WccBannerDto wccBannerDto;
		for (WccBanner wccBanner : wccBannerList) {
			wccBannerDto = new WccBannerDto();
			BeanUtils.copyProperties(wccBanner, wccBannerDto);
			wccBannerDto.setResourceId(wccBanner.getResource().getId());
			wccBannerDto.setBannerPositionText(wccBanner.getBannerPosition() == null ? "" : wccBanner.getBannerPosition().getText());
			
			list.add(wccBannerDto);
		}
		return new ResultMap(ResultMap.SUCCESS_CODE, PageMap.of(count, list));
	}
	
}
