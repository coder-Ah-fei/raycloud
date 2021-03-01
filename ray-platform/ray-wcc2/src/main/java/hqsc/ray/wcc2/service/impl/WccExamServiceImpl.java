package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccExamDto;
import hqsc.ray.wcc2.entity.WccExam;
import hqsc.ray.wcc2.form.WccExamForm;
import hqsc.ray.wcc2.repository.WccExamRepository;
import hqsc.ray.wcc2.service.WccExamService;
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
		Specification<WccExam> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccExam> wccExamList;
		if (wccExamForm.getPageNow() == -1) {
			wccExamList = wccExamRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccExamForm.getPageNow() - 1, wccExamForm.getPageSize());
			Page<WccExam> wccExamPage = wccExamRepository.findAll(specification, pageable);
			wccExamList = wccExamPage.getContent();
		}
		List<WccExamDto> list = new ArrayList<>();
		WccExamDto wccExamDto;
		for (WccExam wccExam : wccExamList) {
			wccExamDto = new WccExamDto();
			BeanUtils.copyProperties(wccExam, wccExamDto);
			
			
			list.add(wccExamDto);
		}
		long count = wccExamRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
