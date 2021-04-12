package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccUserPurchasedCourseDto;
import hqsc.ray.wcc.jpa.entity.JpaWccUserPurchasedCourse;
import hqsc.ray.wcc.jpa.form.WccUserPurchasedCourseForm;
import hqsc.ray.wcc.jpa.repository.WccUserPurchasedCourseRepository;
import hqsc.ray.wcc.jpa.service.WccUserPurchasedCourseService;
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
		Specification<JpaWccUserPurchasedCourse> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccUserPurchasedCourse> jpaWccUserPurchasedCourseList;
		if (wccUserPurchasedCourseForm.getPageNow() == -1) {
			jpaWccUserPurchasedCourseList = wccUserPurchasedCourseRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserPurchasedCourseForm.getPageNow() - 1, wccUserPurchasedCourseForm.getPageSize());
			Page<JpaWccUserPurchasedCourse> wccUserPurchasedCoursePage = wccUserPurchasedCourseRepository.findAll(specification, pageable);
			jpaWccUserPurchasedCourseList = wccUserPurchasedCoursePage.getContent();
		}
		List<WccUserPurchasedCourseDto> list = new ArrayList<>();
		WccUserPurchasedCourseDto wccUserPurchasedCourseDto;
		for (JpaWccUserPurchasedCourse jpaWccUserPurchasedCourse : jpaWccUserPurchasedCourseList) {
			wccUserPurchasedCourseDto = new WccUserPurchasedCourseDto();
			BeanUtils.copyProperties(jpaWccUserPurchasedCourse, wccUserPurchasedCourseDto);
			
			
			list.add(wccUserPurchasedCourseDto);
		}
		long count = wccUserPurchasedCourseRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
