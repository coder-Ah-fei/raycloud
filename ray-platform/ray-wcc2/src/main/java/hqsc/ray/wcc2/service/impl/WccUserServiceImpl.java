package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccUserDto;
import hqsc.ray.wcc2.entity.WccUser;
import hqsc.ray.wcc2.form.WccUserForm;
import hqsc.ray.wcc2.repository.WccUserRepository;
import hqsc.ray.wcc2.service.WccUserService;
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
public class WccUserServiceImpl implements WccUserService {
	
	@Autowired
	private WccUserRepository wccUserRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUsers(WccUserForm wccUserForm) {
		Specification<WccUser> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccUser> wccUserList;
		if (wccUserForm.getPageNow() == -1) {
			wccUserList = wccUserRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserForm.getPageNow() - 1, wccUserForm.getPageSize());
			Page<WccUser> wccUserPage = wccUserRepository.findAll(specification, pageable);
			wccUserList = wccUserPage.getContent();
		}
		List<WccUserDto> list = new ArrayList<>();
		WccUserDto wccUserDto;
		for (WccUser wccUser : wccUserList) {
			wccUserDto = new WccUserDto();
			BeanUtils.copyProperties(wccUser, wccUserDto);
			
			
			list.add(wccUserDto);
		}
		long count = wccUserRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
