package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysBlacklistDto;
import hqsc.ray.wcc2.entity.SysBlacklist;
import hqsc.ray.wcc2.form.SysBlacklistForm;
import hqsc.ray.wcc2.repository.SysBlacklistRepository;
import hqsc.ray.wcc2.service.SysBlacklistService;
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
public class SysBlacklistServiceImpl implements SysBlacklistService {
	
	@Autowired
	private SysBlacklistRepository sysBlacklistRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysBlacklistForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysBlacklists(SysBlacklistForm sysBlacklistForm) {
		Specification<SysBlacklist> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysBlacklist> sysBlacklistList;
		if (sysBlacklistForm.getPageNow() == -1) {
			sysBlacklistList = sysBlacklistRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysBlacklistForm.getPageNow() - 1, sysBlacklistForm.getPageSize());
			Page<SysBlacklist> sysBlacklistPage = sysBlacklistRepository.findAll(specification, pageable);
			sysBlacklistList = sysBlacklistPage.getContent();
		}
		List<SysBlacklistDto> list = new ArrayList<>();
		SysBlacklistDto sysBlacklistDto;
		for (SysBlacklist sysBlacklist : sysBlacklistList) {
			sysBlacklistDto = new SysBlacklistDto();
			BeanUtils.copyProperties(sysBlacklist, sysBlacklistDto);
			
			
			list.add(sysBlacklistDto);
		}
		long count = sysBlacklistRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
