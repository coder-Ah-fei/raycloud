package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysRouteDto;
import hqsc.ray.wcc2.entity.SysRoute;
import hqsc.ray.wcc2.form.SysRouteForm;
import hqsc.ray.wcc2.repository.SysRouteRepository;
import hqsc.ray.wcc2.service.SysRouteService;
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
public class SysRouteServiceImpl implements SysRouteService {
	
	@Autowired
	private SysRouteRepository sysRouteRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysRouteForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysRoutes(SysRouteForm sysRouteForm) {
		Specification<SysRoute> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysRoute> sysRouteList;
		if (sysRouteForm.getPageNow() == -1) {
			sysRouteList = sysRouteRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysRouteForm.getPageNow() - 1, sysRouteForm.getPageSize());
			Page<SysRoute> sysRoutePage = sysRouteRepository.findAll(specification, pageable);
			sysRouteList = sysRoutePage.getContent();
		}
		List<SysRouteDto> list = new ArrayList<>();
		SysRouteDto sysRouteDto;
		for (SysRoute sysRoute : sysRouteList) {
			sysRouteDto = new SysRouteDto();
			BeanUtils.copyProperties(sysRoute, sysRouteDto);
			
			
			list.add(sysRouteDto);
		}
		long count = sysRouteRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
