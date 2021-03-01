package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccUserCircleDto;
import hqsc.ray.wcc2.entity.WccUserCircle;
import hqsc.ray.wcc2.form.WccUserCircleForm;
import hqsc.ray.wcc2.repository.WccUserCircleRepository;
import hqsc.ray.wcc2.service.WccUserCircleService;
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
public class WccUserCircleServiceImpl implements WccUserCircleService {
	
	@Autowired
	private WccUserCircleRepository wccUserCircleRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserCircleForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUserCircles(WccUserCircleForm wccUserCircleForm) {
		Specification<WccUserCircle> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccUserCircle> wccUserCircleList;
		if (wccUserCircleForm.getPageNow() == -1) {
			wccUserCircleList = wccUserCircleRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserCircleForm.getPageNow() - 1, wccUserCircleForm.getPageSize());
			Page<WccUserCircle> wccUserCirclePage = wccUserCircleRepository.findAll(specification, pageable);
			wccUserCircleList = wccUserCirclePage.getContent();
		}
		List<WccUserCircleDto> list = new ArrayList<>();
		WccUserCircleDto wccUserCircleDto;
		for (WccUserCircle wccUserCircle : wccUserCircleList) {
			wccUserCircleDto = new WccUserCircleDto();
			BeanUtils.copyProperties(wccUserCircle, wccUserCircleDto);
			
			
			list.add(wccUserCircleDto);
		}
		long count = wccUserCircleRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
