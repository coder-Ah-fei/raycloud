package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccExamDto;
import hqsc.ray.wcc.jpa.entity.JpaWccExam;
import hqsc.ray.wcc.jpa.form.WccExamForm;
import hqsc.ray.wcc.jpa.repository.WccExamRepository;
import hqsc.ray.wcc.jpa.service.WccExamService;
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
public class WccExamServiceImpl implements WccExamService {
	
	@Autowired
	private WccExamRepository wccExamRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccExamForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccExams(WccExamForm wccExamForm) {
		Specification<JpaWccExam> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccExam> jpaWccExamList;
		if (wccExamForm.getPageNow() == -1) {
			jpaWccExamList = wccExamRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccExamForm.getPageNow() - 1, wccExamForm.getPageSize());
			Page<JpaWccExam> wccExamPage = wccExamRepository.findAll(specification, pageable);
			jpaWccExamList = wccExamPage.getContent();
		}
		List<WccExamDto> list = new ArrayList<>();
		WccExamDto wccExamDto;
		for (JpaWccExam jpaWccExam : jpaWccExamList) {
			wccExamDto = new WccExamDto();
			BeanUtils.copyProperties(jpaWccExam, wccExamDto);
			
			
			list.add(wccExamDto);
		}
		long count = wccExamRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
