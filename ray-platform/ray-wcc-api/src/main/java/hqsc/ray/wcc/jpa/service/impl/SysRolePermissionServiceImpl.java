package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.SysRolePermissionDto;
import hqsc.ray.wcc.jpa.entity.JpaSysRolePermission;
import hqsc.ray.wcc.jpa.form.SysRolePermissionForm;
import hqsc.ray.wcc.jpa.repository.SysRolePermissionRepository;
import hqsc.ray.wcc.jpa.service.SysRolePermissionService;
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
		Specification<JpaSysRolePermission> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysRolePermission> jpaSysRolePermissionList;
		if (sysRolePermissionForm.getPageNow() == -1) {
			jpaSysRolePermissionList = sysRolePermissionRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysRolePermissionForm.getPageNow() - 1, sysRolePermissionForm.getPageSize());
			Page<JpaSysRolePermission> sysRolePermissionPage = sysRolePermissionRepository.findAll(specification, pageable);
			jpaSysRolePermissionList = sysRolePermissionPage.getContent();
		}
		List<SysRolePermissionDto> list = new ArrayList<>();
		SysRolePermissionDto sysRolePermissionDto;
		for (JpaSysRolePermission jpaSysRolePermission : jpaSysRolePermissionList) {
			sysRolePermissionDto = new SysRolePermissionDto();
			BeanUtils.copyProperties(jpaSysRolePermission, sysRolePermissionDto);
			
			
			list.add(sysRolePermissionDto);
		}
		long count = sysRolePermissionRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
