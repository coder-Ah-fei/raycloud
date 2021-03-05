package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccBrandMerchantAuthDto;
import hqsc.ray.wcc.jpa.entity.JpaWccBrandMerchantAuth;
import hqsc.ray.wcc.jpa.form.WccBrandMerchantAuthForm;
import hqsc.ray.wcc.jpa.repository.WccBrandMerchantAuthRepository;
import hqsc.ray.wcc.jpa.service.WccBrandMerchantAuthService;
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
public class WccBrandMerchantAuthServiceImpl implements WccBrandMerchantAuthService {
	
	@Autowired
	private WccBrandMerchantAuthRepository wccBrandMerchantAuthRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccBrandMerchantAuthForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccBrandMerchantAuths(WccBrandMerchantAuthForm wccBrandMerchantAuthForm) {
		Specification<JpaWccBrandMerchantAuth> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccBrandMerchantAuth> jpaWccBrandMerchantAuthList;
		if (wccBrandMerchantAuthForm.getPageNow() == -1) {
			jpaWccBrandMerchantAuthList = wccBrandMerchantAuthRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccBrandMerchantAuthForm.getPageNow() - 1, wccBrandMerchantAuthForm.getPageSize());
			Page<JpaWccBrandMerchantAuth> wccBrandMerchantAuthPage = wccBrandMerchantAuthRepository.findAll(specification, pageable);
			jpaWccBrandMerchantAuthList = wccBrandMerchantAuthPage.getContent();
		}
		List<WccBrandMerchantAuthDto> list = new ArrayList<>();
		WccBrandMerchantAuthDto wccBrandMerchantAuthDto;
		for (JpaWccBrandMerchantAuth jpaWccBrandMerchantAuth : jpaWccBrandMerchantAuthList) {
			wccBrandMerchantAuthDto = new WccBrandMerchantAuthDto();
			BeanUtils.copyProperties(jpaWccBrandMerchantAuth, wccBrandMerchantAuthDto);
			
			
			list.add(wccBrandMerchantAuthDto);
		}
		long count = wccBrandMerchantAuthRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
