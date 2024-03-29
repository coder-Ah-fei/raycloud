package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.SysUserDto;
import hqsc.ray.wcc.jpa.entity.JpaSysUser;
import hqsc.ray.wcc.jpa.form.SysUserForm;
import hqsc.ray.wcc.jpa.repository.SysUserRepository;
import hqsc.ray.wcc.jpa.service.SysUserService;
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
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserRepository sysUserRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysUserForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysUsers(SysUserForm sysUserForm) {
		Specification<JpaSysUser> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysUser> jpaSysUserList;
		if (sysUserForm.getPageNow() == -1) {
			jpaSysUserList = sysUserRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysUserForm.getPageNow() - 1, sysUserForm.getPageSize());
			Page<JpaSysUser> sysUserPage = sysUserRepository.findAll(specification, pageable);
			jpaSysUserList = sysUserPage.getContent();
		}
		List<SysUserDto> list = new ArrayList<>();
		SysUserDto sysUserDto;
		for (JpaSysUser jpaSysUser : jpaSysUserList) {
			sysUserDto = new SysUserDto();
			BeanUtils.copyProperties(jpaSysUser, sysUserDto);
			
			
			list.add(sysUserDto);
		}
		long count = sysUserRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.of(ResultMap.SUCCESS_CODE, map);
	}
	
}
