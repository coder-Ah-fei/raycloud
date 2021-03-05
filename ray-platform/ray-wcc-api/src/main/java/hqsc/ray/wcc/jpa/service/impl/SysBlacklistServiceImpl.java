package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.SysBlacklistDto;
import hqsc.ray.wcc.jpa.entity.JpaSysBlacklist;
import hqsc.ray.wcc.jpa.form.SysBlacklistForm;
import hqsc.ray.wcc.jpa.repository.SysBlacklistRepository;
import hqsc.ray.wcc.jpa.service.SysBlacklistService;
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
		Specification<JpaSysBlacklist> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysBlacklist> jpaSysBlacklistList;
		if (sysBlacklistForm.getPageNow() == -1) {
			jpaSysBlacklistList = sysBlacklistRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysBlacklistForm.getPageNow() - 1, sysBlacklistForm.getPageSize());
			Page<JpaSysBlacklist> sysBlacklistPage = sysBlacklistRepository.findAll(specification, pageable);
			jpaSysBlacklistList = sysBlacklistPage.getContent();
		}
		List<SysBlacklistDto> list = new ArrayList<>();
		SysBlacklistDto sysBlacklistDto;
		for (JpaSysBlacklist jpaSysBlacklist : jpaSysBlacklistList) {
			sysBlacklistDto = new SysBlacklistDto();
			BeanUtils.copyProperties(jpaSysBlacklist, sysBlacklistDto);
			
			
			list.add(sysBlacklistDto);
		}
		long count = sysBlacklistRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
