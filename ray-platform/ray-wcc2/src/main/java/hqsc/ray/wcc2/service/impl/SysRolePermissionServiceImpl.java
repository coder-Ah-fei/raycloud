package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysRolePermissionDto;
import hqsc.ray.wcc2.entity.SysRolePermission;
import hqsc.ray.wcc2.form.SysRolePermissionForm;
import hqsc.ray.wcc2.repository.SysRolePermissionRepository;
import hqsc.ray.wcc2.service.SysRolePermissionService;
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
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
	
	@Autowired
	private SysRolePermissionRepository sysRolePermissionRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysRolePermissionForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysRolePermissions(SysRolePermissionForm sysRolePermissionForm) {
		Specification<SysRolePermission> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysRolePermission> sysRolePermissionList;
		if (sysRolePermissionForm.getPageNow() == -1) {
			sysRolePermissionList = sysRolePermissionRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysRolePermissionForm.getPageNow() - 1, sysRolePermissionForm.getPageSize());
			Page<SysRolePermission> sysRolePermissionPage = sysRolePermissionRepository.findAll(specification, pageable);
			sysRolePermissionList = sysRolePermissionPage.getContent();
		}
		List<SysRolePermissionDto> list = new ArrayList<>();
		SysRolePermissionDto sysRolePermissionDto;
		for (SysRolePermission sysRolePermission : sysRolePermissionList) {
			sysRolePermissionDto = new SysRolePermissionDto();
			BeanUtils.copyProperties(sysRolePermission, sysRolePermissionDto);
			
			
			list.add(sysRolePermissionDto);
		}
		long count = sysRolePermissionRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
