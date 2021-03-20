package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccTalentAuthDto;
import hqsc.ray.wcc.jpa.entity.JpaWccTalentAuth;
import hqsc.ray.wcc.jpa.form.WccTalentAuthForm;
import hqsc.ray.wcc.jpa.repository.WccTalentAuthRepository;
import hqsc.ray.wcc.jpa.service.WccTalentAuthService;
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
public class WccTalentAuthServiceImpl implements WccTalentAuthService {
	
	@Autowired
	private WccTalentAuthRepository wccTalentAuthRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccTalentAuthForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccTalentAuths(WccTalentAuthForm wccTalentAuthForm) {
		Specification<JpaWccTalentAuth> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccTalentAuth> jpaWccTalentAuthList;
		if (wccTalentAuthForm.getPageNow() == -1) {
			jpaWccTalentAuthList = wccTalentAuthRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccTalentAuthForm.getPageNow() - 1, wccTalentAuthForm.getPageSize());
			Page<JpaWccTalentAuth> wccTalentAuthPage = wccTalentAuthRepository.findAll(specification, pageable);
			jpaWccTalentAuthList = wccTalentAuthPage.getContent();
		}
		List<WccTalentAuthDto> list = new ArrayList<>();
		WccTalentAuthDto wccTalentAuthDto;
		for (JpaWccTalentAuth jpaWccTalentAuth : jpaWccTalentAuthList) {
			wccTalentAuthDto = new WccTalentAuthDto();
			BeanUtils.copyProperties(jpaWccTalentAuth, wccTalentAuthDto);
			
			
			list.add(wccTalentAuthDto);
		}
		long count = wccTalentAuthRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
