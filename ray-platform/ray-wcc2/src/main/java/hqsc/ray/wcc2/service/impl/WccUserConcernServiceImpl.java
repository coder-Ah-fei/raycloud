package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccUserConcernDto;
import hqsc.ray.wcc2.entity.WccUserConcern;
import hqsc.ray.wcc2.form.WccUserConcernForm;
import hqsc.ray.wcc2.repository.WccUserConcernRepository;
import hqsc.ray.wcc2.service.WccUserConcernService;
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
public class WccUserConcernServiceImpl implements WccUserConcernService {
	
	@Autowired
	private WccUserConcernRepository wccUserConcernRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserConcernForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUserConcerns(WccUserConcernForm wccUserConcernForm) {
		Specification<WccUserConcern> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccUserConcern> wccUserConcernList;
		if (wccUserConcernForm.getPageNow() == -1) {
			wccUserConcernList = wccUserConcernRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserConcernForm.getPageNow() - 1, wccUserConcernForm.getPageSize());
			Page<WccUserConcern> wccUserConcernPage = wccUserConcernRepository.findAll(specification, pageable);
			wccUserConcernList = wccUserConcernPage.getContent();
		}
		List<WccUserConcernDto> list = new ArrayList<>();
		WccUserConcernDto wccUserConcernDto;
		for (WccUserConcern wccUserConcern : wccUserConcernList) {
			wccUserConcernDto = new WccUserConcernDto();
			BeanUtils.copyProperties(wccUserConcern, wccUserConcernDto);
			
			
			list.add(wccUserConcernDto);
		}
		long count = wccUserConcernRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
