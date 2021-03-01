package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysMenuDto;
import hqsc.ray.wcc2.entity.SysMenu;
import hqsc.ray.wcc2.form.SysMenuForm;
import hqsc.ray.wcc2.repository.SysMenuRepository;
import hqsc.ray.wcc2.service.SysMenuService;
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
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuRepository sysMenuRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysMenuForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysMenus(SysMenuForm sysMenuForm) {
		Specification<SysMenu> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysMenu> sysMenuList;
		if (sysMenuForm.getPageNow() == -1) {
			sysMenuList = sysMenuRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysMenuForm.getPageNow() - 1, sysMenuForm.getPageSize());
			Page<SysMenu> sysMenuPage = sysMenuRepository.findAll(specification, pageable);
			sysMenuList = sysMenuPage.getContent();
		}
		List<SysMenuDto> list = new ArrayList<>();
		SysMenuDto sysMenuDto;
		for (SysMenu sysMenu : sysMenuList) {
			sysMenuDto = new SysMenuDto();
			BeanUtils.copyProperties(sysMenu, sysMenuDto);
			
			
			list.add(sysMenuDto);
		}
		long count = sysMenuRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
