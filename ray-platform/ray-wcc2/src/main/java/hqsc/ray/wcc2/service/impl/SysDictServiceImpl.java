package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysDictDto;
import hqsc.ray.wcc2.entity.SysDict;
import hqsc.ray.wcc2.form.SysDictForm;
import hqsc.ray.wcc2.repository.SysDictRepository;
import hqsc.ray.wcc2.service.SysDictService;
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
public class SysDictServiceImpl implements SysDictService {
	
	@Autowired
	private SysDictRepository sysDictRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysDictForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysDicts(SysDictForm sysDictForm) {
		Specification<SysDict> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysDict> sysDictList;
		if (sysDictForm.getPageNow() == -1) {
			sysDictList = sysDictRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysDictForm.getPageNow() - 1, sysDictForm.getPageSize());
			Page<SysDict> sysDictPage = sysDictRepository.findAll(specification, pageable);
			sysDictList = sysDictPage.getContent();
		}
		List<SysDictDto> list = new ArrayList<>();
		SysDictDto sysDictDto;
		for (SysDict sysDict : sysDictList) {
			sysDictDto = new SysDictDto();
			BeanUtils.copyProperties(sysDict, sysDictDto);
			
			
			list.add(sysDictDto);
		}
		long count = sysDictRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
