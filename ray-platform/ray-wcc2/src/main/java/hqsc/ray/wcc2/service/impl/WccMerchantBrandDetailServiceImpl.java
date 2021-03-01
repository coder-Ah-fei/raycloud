package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccMerchantBrandDetailDto;
import hqsc.ray.wcc2.entity.WccMerchantBrandDetail;
import hqsc.ray.wcc2.form.WccMerchantBrandDetailForm;
import hqsc.ray.wcc2.repository.WccMerchantBrandDetailRepository;
import hqsc.ray.wcc2.service.WccMerchantBrandDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccMerchantBrandDetailServiceImpl implements WccMerchantBrandDetailService {
	
	@Autowired
	private WccMerchantBrandDetailRepository wccMerchantBrandDetailRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccMerchantBrandDetailForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccMerchantBrandDetails(WccMerchantBrandDetailForm wccMerchantBrandDetailForm) {
		Specification<WccMerchantBrandDetail> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccMerchantBrandDetail> wccMerchantBrandDetailList;
		if (wccMerchantBrandDetailForm.getPageNow() == -1) {
			wccMerchantBrandDetailList = wccMerchantBrandDetailRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccMerchantBrandDetailForm.getPageNow() - 1, wccMerchantBrandDetailForm.getPageSize());
			Page<WccMerchantBrandDetail> wccMerchantBrandDetailPage = wccMerchantBrandDetailRepository.findAll(specification, pageable);
			wccMerchantBrandDetailList = wccMerchantBrandDetailPage.getContent();
		}
		List<WccMerchantBrandDetailDto> list = new ArrayList<>();
		WccMerchantBrandDetailDto wccMerchantBrandDetailDto;
		for (WccMerchantBrandDetail wccMerchantBrandDetail : wccMerchantBrandDetailList) {
			wccMerchantBrandDetailDto = new WccMerchantBrandDetailDto();
			BeanUtils.copyProperties(wccMerchantBrandDetail, wccMerchantBrandDetailDto);
			
			
			list.add(wccMerchantBrandDetailDto);
		}
		long count = wccMerchantBrandDetailRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
