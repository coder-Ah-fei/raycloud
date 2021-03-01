package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccOrganizationDetailDto;
import hqsc.ray.wcc2.entity.WccOrganizationDetail;
import hqsc.ray.wcc2.form.WccOrganizationDetailForm;
import hqsc.ray.wcc2.repository.WccOrganizationDetailRepository;
import hqsc.ray.wcc2.service.WccOrganizationDetailService;
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
public class WccOrganizationDetailServiceImpl implements WccOrganizationDetailService {
	
	@Autowired
	private WccOrganizationDetailRepository wccOrganizationDetailRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccOrganizationDetailForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccOrganizationDetails(WccOrganizationDetailForm wccOrganizationDetailForm) {
		Specification<WccOrganizationDetail> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccOrganizationDetail> wccOrganizationDetailList;
		if (wccOrganizationDetailForm.getPageNow() == -1) {
			wccOrganizationDetailList = wccOrganizationDetailRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccOrganizationDetailForm.getPageNow() - 1, wccOrganizationDetailForm.getPageSize());
			Page<WccOrganizationDetail> wccOrganizationDetailPage = wccOrganizationDetailRepository.findAll(specification, pageable);
			wccOrganizationDetailList = wccOrganizationDetailPage.getContent();
		}
		List<WccOrganizationDetailDto> list = new ArrayList<>();
		WccOrganizationDetailDto wccOrganizationDetailDto;
		for (WccOrganizationDetail wccOrganizationDetail : wccOrganizationDetailList) {
			wccOrganizationDetailDto = new WccOrganizationDetailDto();
			BeanUtils.copyProperties(wccOrganizationDetail, wccOrganizationDetailDto);
			
			
			list.add(wccOrganizationDetailDto);
		}
		long count = wccOrganizationDetailRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
