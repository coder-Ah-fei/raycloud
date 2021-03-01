package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccAuthCenterDto;
import hqsc.ray.wcc2.entity.WccAuthCenter;
import hqsc.ray.wcc2.form.WccAuthCenterForm;
import hqsc.ray.wcc2.repository.WccAuthCenterRepository;
import hqsc.ray.wcc2.service.WccAuthCenterService;
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
public class WccAuthCenterServiceImpl implements WccAuthCenterService {
	
	@Autowired
	private WccAuthCenterRepository wccAuthCenterRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccAuthCenterForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccAuthCenters(WccAuthCenterForm wccAuthCenterForm) {
		Specification<WccAuthCenter> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccAuthCenter> wccAuthCenterList;
		if (wccAuthCenterForm.getPageNow() == -1) {
			wccAuthCenterList = wccAuthCenterRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccAuthCenterForm.getPageNow() - 1, wccAuthCenterForm.getPageSize());
			Page<WccAuthCenter> wccAuthCenterPage = wccAuthCenterRepository.findAll(specification, pageable);
			wccAuthCenterList = wccAuthCenterPage.getContent();
		}
		List<WccAuthCenterDto> list = new ArrayList<>();
		WccAuthCenterDto wccAuthCenterDto;
		for (WccAuthCenter wccAuthCenter : wccAuthCenterList) {
			wccAuthCenterDto = new WccAuthCenterDto();
			BeanUtils.copyProperties(wccAuthCenter, wccAuthCenterDto);
			
			
			list.add(wccAuthCenterDto);
		}
		long count = wccAuthCenterRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
