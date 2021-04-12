package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccOrganizationDetailDto;
import hqsc.ray.wcc.jpa.entity.JpaWccOrganizationDetail;
import hqsc.ray.wcc.jpa.form.WccOrganizationDetailForm;
import hqsc.ray.wcc.jpa.repository.WccOrganizationDetailRepository;
import hqsc.ray.wcc.jpa.service.WccOrganizationDetailService;
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
		Specification<JpaWccOrganizationDetail> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccOrganizationDetail> jpaWccOrganizationDetailList;
		if (wccOrganizationDetailForm.getPageNow() == -1) {
			jpaWccOrganizationDetailList = wccOrganizationDetailRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccOrganizationDetailForm.getPageNow() - 1, wccOrganizationDetailForm.getPageSize());
			Page<JpaWccOrganizationDetail> wccOrganizationDetailPage = wccOrganizationDetailRepository.findAll(specification, pageable);
			jpaWccOrganizationDetailList = wccOrganizationDetailPage.getContent();
		}
		List<WccOrganizationDetailDto> list = new ArrayList<>();
		WccOrganizationDetailDto wccOrganizationDetailDto;
		for (JpaWccOrganizationDetail jpaWccOrganizationDetail : jpaWccOrganizationDetailList) {
			wccOrganizationDetailDto = new WccOrganizationDetailDto();
			BeanUtils.copyProperties(jpaWccOrganizationDetail, wccOrganizationDetailDto);
			
			
			list.add(wccOrganizationDetailDto);
		}
		long count = wccOrganizationDetailRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
