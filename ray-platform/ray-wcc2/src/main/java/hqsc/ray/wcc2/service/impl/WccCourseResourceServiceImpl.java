package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccCourseResourceDto;
import hqsc.ray.wcc2.entity.WccCourseResource;
import hqsc.ray.wcc2.form.WccCourseResourceForm;
import hqsc.ray.wcc2.repository.WccCourseResourceRepository;
import hqsc.ray.wcc2.service.WccCourseResourceService;
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
public class WccCourseResourceServiceImpl implements WccCourseResourceService {
	
	@Autowired
	private WccCourseResourceRepository wccCourseResourceRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccCourseResourceForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccCourseResources(WccCourseResourceForm wccCourseResourceForm) {
		Specification<WccCourseResource> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccCourseResource> wccCourseResourceList;
		if (wccCourseResourceForm.getPageNow() == -1) {
			wccCourseResourceList = wccCourseResourceRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccCourseResourceForm.getPageNow() - 1, wccCourseResourceForm.getPageSize());
			Page<WccCourseResource> wccCourseResourcePage = wccCourseResourceRepository.findAll(specification, pageable);
			wccCourseResourceList = wccCourseResourcePage.getContent();
		}
		List<WccCourseResourceDto> list = new ArrayList<>();
		WccCourseResourceDto wccCourseResourceDto;
		for (WccCourseResource wccCourseResource : wccCourseResourceList) {
			wccCourseResourceDto = new WccCourseResourceDto();
			BeanUtils.copyProperties(wccCourseResource, wccCourseResourceDto);
			
			
			list.add(wccCourseResourceDto);
		}
		long count = wccCourseResourceRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
