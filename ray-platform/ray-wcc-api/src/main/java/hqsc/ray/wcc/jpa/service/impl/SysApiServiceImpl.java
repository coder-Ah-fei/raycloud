package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.SysApiDto;
import hqsc.ray.wcc.jpa.entity.JpaSysApi;
import hqsc.ray.wcc.jpa.form.SysApiForm;
import hqsc.ray.wcc.jpa.repository.SysApiRepository;
import hqsc.ray.wcc.jpa.service.SysApiService;
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
		Specification<JpaSysApi> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysApi> jpaSysApiList;
		if (sysApiForm.getPageNow() == -1) {
			jpaSysApiList = sysApiRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysApiForm.getPageNow() - 1, sysApiForm.getPageSize());
			Page<JpaSysApi> sysApiPage = sysApiRepository.findAll(specification, pageable);
			jpaSysApiList = sysApiPage.getContent();
		}
		List<SysApiDto> list = new ArrayList<>();
		SysApiDto sysApiDto;
		for (JpaSysApi jpaSysApi : jpaSysApiList) {
			sysApiDto = new SysApiDto();
			BeanUtils.copyProperties(jpaSysApi, sysApiDto);
			
			
			list.add(sysApiDto);
		}
		long count = sysApiRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
