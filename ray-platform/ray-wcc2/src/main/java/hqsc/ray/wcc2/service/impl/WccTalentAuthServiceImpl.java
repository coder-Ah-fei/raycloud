package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccTalentAuthDto;
import hqsc.ray.wcc2.entity.WccTalentAuth;
import hqsc.ray.wcc2.form.WccTalentAuthForm;
import hqsc.ray.wcc2.repository.WccTalentAuthRepository;
import hqsc.ray.wcc2.service.WccTalentAuthService;
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
		Specification<WccTalentAuth> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccTalentAuth> wccTalentAuthList;
		if (wccTalentAuthForm.getPageNow() == -1) {
			wccTalentAuthList = wccTalentAuthRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccTalentAuthForm.getPageNow() - 1, wccTalentAuthForm.getPageSize());
			Page<WccTalentAuth> wccTalentAuthPage = wccTalentAuthRepository.findAll(specification, pageable);
			wccTalentAuthList = wccTalentAuthPage.getContent();
		}
		List<WccTalentAuthDto> list = new ArrayList<>();
		WccTalentAuthDto wccTalentAuthDto;
		for (WccTalentAuth wccTalentAuth : wccTalentAuthList) {
			wccTalentAuthDto = new WccTalentAuthDto();
			BeanUtils.copyProperties(wccTalentAuth, wccTalentAuthDto);
			
			
			list.add(wccTalentAuthDto);
		}
		long count = wccTalentAuthRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
