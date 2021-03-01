package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccUserPurchasedCourseDto;
import hqsc.ray.wcc2.entity.WccUserPurchasedCourse;
import hqsc.ray.wcc2.form.WccUserPurchasedCourseForm;
import hqsc.ray.wcc2.repository.WccUserPurchasedCourseRepository;
import hqsc.ray.wcc2.service.WccUserPurchasedCourseService;
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
public class WccUserPurchasedCourseServiceImpl implements WccUserPurchasedCourseService {
	
	@Autowired
	private WccUserPurchasedCourseRepository wccUserPurchasedCourseRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserPurchasedCourseForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUserPurchasedCourses(WccUserPurchasedCourseForm wccUserPurchasedCourseForm) {
		Specification<WccUserPurchasedCourse> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccUserPurchasedCourse> wccUserPurchasedCourseList;
		if (wccUserPurchasedCourseForm.getPageNow() == -1) {
			wccUserPurchasedCourseList = wccUserPurchasedCourseRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserPurchasedCourseForm.getPageNow() - 1, wccUserPurchasedCourseForm.getPageSize());
			Page<WccUserPurchasedCourse> wccUserPurchasedCoursePage = wccUserPurchasedCourseRepository.findAll(specification, pageable);
			wccUserPurchasedCourseList = wccUserPurchasedCoursePage.getContent();
		}
		List<WccUserPurchasedCourseDto> list = new ArrayList<>();
		WccUserPurchasedCourseDto wccUserPurchasedCourseDto;
		for (WccUserPurchasedCourse wccUserPurchasedCourse : wccUserPurchasedCourseList) {
			wccUserPurchasedCourseDto = new WccUserPurchasedCourseDto();
			BeanUtils.copyProperties(wccUserPurchasedCourse, wccUserPurchasedCourseDto);
			
			
			list.add(wccUserPurchasedCourseDto);
		}
		long count = wccUserPurchasedCourseRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
