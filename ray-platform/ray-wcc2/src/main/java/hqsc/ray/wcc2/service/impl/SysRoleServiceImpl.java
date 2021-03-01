package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysRoleDto;
import hqsc.ray.wcc2.entity.SysRole;
import hqsc.ray.wcc2.form.SysRoleForm;
import hqsc.ray.wcc2.repository.SysRoleRepository;
import hqsc.ray.wcc2.service.SysRoleService;
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
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	private SysRoleRepository sysRoleRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysRoleForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysRoles(SysRoleForm sysRoleForm) {
		Specification<SysRole> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysRole> sysRoleList;
		if (sysRoleForm.getPageNow() == -1) {
			sysRoleList = sysRoleRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysRoleForm.getPageNow() - 1, sysRoleForm.getPageSize());
			Page<SysRole> sysRolePage = sysRoleRepository.findAll(specification, pageable);
			sysRoleList = sysRolePage.getContent();
		}
		List<SysRoleDto> list = new ArrayList<>();
		SysRoleDto sysRoleDto;
		for (SysRole sysRole : sysRoleList) {
			sysRoleDto = new SysRoleDto();
			BeanUtils.copyProperties(sysRole, sysRoleDto);
			
			
			list.add(sysRoleDto);
		}
		long count = sysRoleRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
