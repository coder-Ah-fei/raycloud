package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.SysRoleDto;
import hqsc.ray.wcc.jpa.entity.JpaSysRole;
import hqsc.ray.wcc.jpa.form.SysRoleForm;
import hqsc.ray.wcc.jpa.repository.SysRoleRepository;
import hqsc.ray.wcc.jpa.service.SysRoleService;
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
		Specification<JpaSysRole> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysRole> jpaSysRoleList;
		if (sysRoleForm.getPageNow() == -1) {
			jpaSysRoleList = sysRoleRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysRoleForm.getPageNow() - 1, sysRoleForm.getPageSize());
			Page<JpaSysRole> sysRolePage = sysRoleRepository.findAll(specification, pageable);
			jpaSysRoleList = sysRolePage.getContent();
		}
		List<SysRoleDto> list = new ArrayList<>();
		SysRoleDto sysRoleDto;
		for (JpaSysRole jpaSysRole : jpaSysRoleList) {
			sysRoleDto = new SysRoleDto();
			BeanUtils.copyProperties(jpaSysRole, sysRoleDto);
			
			
			list.add(sysRoleDto);
		}
		long count = sysRoleRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
