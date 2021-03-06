package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysDepartDto;
import hqsc.ray.wcc2.entity.SysDepart;
import hqsc.ray.wcc2.form.SysDepartForm;
import hqsc.ray.wcc2.repository.SysDepartRepository;
import hqsc.ray.wcc2.service.SysDepartService;
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
		Specification<SysDepart> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysDepart> sysDepartList;
		if (sysDepartForm.getPageNow() == -1) {
			sysDepartList = sysDepartRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysDepartForm.getPageNow() - 1, sysDepartForm.getPageSize());
			Page<SysDepart> sysDepartPage = sysDepartRepository.findAll(specification, pageable);
			sysDepartList = sysDepartPage.getContent();
		}
		List<SysDepartDto> list = new ArrayList<>();
		SysDepartDto sysDepartDto;
		for (SysDepart sysDepart : sysDepartList) {
			sysDepartDto = new SysDepartDto();
			BeanUtils.copyProperties(sysDepart, sysDepartDto);
			
			
			list.add(sysDepartDto);
		}
		long count = sysDepartRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
