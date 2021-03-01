package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysApiDto;
import hqsc.ray.wcc2.entity.SysApi;
import hqsc.ray.wcc2.form.SysApiForm;
import hqsc.ray.wcc2.repository.SysApiRepository;
import hqsc.ray.wcc2.service.SysApiService;
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
public class SysApiServiceImpl implements SysApiService {
	
	@Autowired
	private SysApiRepository sysApiRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysApiForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysApis(SysApiForm sysApiForm) {
		Specification<SysApi> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysApi> sysApiList;
		if (sysApiForm.getPageNow() == -1) {
			sysApiList = sysApiRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysApiForm.getPageNow() - 1, sysApiForm.getPageSize());
			Page<SysApi> sysApiPage = sysApiRepository.findAll(specification, pageable);
			sysApiList = sysApiPage.getContent();
		}
		List<SysApiDto> list = new ArrayList<>();
		SysApiDto sysApiDto;
		for (SysApi sysApi : sysApiList) {
			sysApiDto = new SysApiDto();
			BeanUtils.copyProperties(sysApi, sysApiDto);
			
			
			list.add(sysApiDto);
		}
		long count = sysApiRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
