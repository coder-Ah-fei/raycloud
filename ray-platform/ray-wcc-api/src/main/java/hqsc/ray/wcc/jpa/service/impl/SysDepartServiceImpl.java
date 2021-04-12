package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.SysDepartDto;
import hqsc.ray.wcc.jpa.entity.JpaSysDepart;
import hqsc.ray.wcc.jpa.form.SysDepartForm;
import hqsc.ray.wcc.jpa.repository.SysDepartRepository;
import hqsc.ray.wcc.jpa.service.SysDepartService;
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
public class SysDepartServiceImpl implements SysDepartService {
	
	@Autowired
	private SysDepartRepository sysDepartRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysDepartForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysDeparts(SysDepartForm sysDepartForm) {
		Specification<JpaSysDepart> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysDepart> jpaSysDepartList;
		if (sysDepartForm.getPageNow() == -1) {
			jpaSysDepartList = sysDepartRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysDepartForm.getPageNow() - 1, sysDepartForm.getPageSize());
			Page<JpaSysDepart> sysDepartPage = sysDepartRepository.findAll(specification, pageable);
			jpaSysDepartList = sysDepartPage.getContent();
		}
		List<SysDepartDto> list = new ArrayList<>();
		SysDepartDto sysDepartDto;
		for (JpaSysDepart jpaSysDepart : jpaSysDepartList) {
			sysDepartDto = new SysDepartDto();
			BeanUtils.copyProperties(jpaSysDepart, sysDepartDto);
			
			
			list.add(sysDepartDto);
		}
		long count = sysDepartRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
