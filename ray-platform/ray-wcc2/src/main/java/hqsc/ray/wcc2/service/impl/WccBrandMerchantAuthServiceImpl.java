package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccBrandMerchantAuthDto;
import hqsc.ray.wcc2.entity.WccBrandMerchantAuth;
import hqsc.ray.wcc2.form.WccBrandMerchantAuthForm;
import hqsc.ray.wcc2.repository.WccBrandMerchantAuthRepository;
import hqsc.ray.wcc2.service.WccBrandMerchantAuthService;
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
		Specification<WccBrandMerchantAuth> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccBrandMerchantAuth> wccBrandMerchantAuthList;
		if (wccBrandMerchantAuthForm.getPageNow() == -1) {
			wccBrandMerchantAuthList = wccBrandMerchantAuthRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccBrandMerchantAuthForm.getPageNow() - 1, wccBrandMerchantAuthForm.getPageSize());
			Page<WccBrandMerchantAuth> wccBrandMerchantAuthPage = wccBrandMerchantAuthRepository.findAll(specification, pageable);
			wccBrandMerchantAuthList = wccBrandMerchantAuthPage.getContent();
		}
		List<WccBrandMerchantAuthDto> list = new ArrayList<>();
		WccBrandMerchantAuthDto wccBrandMerchantAuthDto;
		for (WccBrandMerchantAuth wccBrandMerchantAuth : wccBrandMerchantAuthList) {
			wccBrandMerchantAuthDto = new WccBrandMerchantAuthDto();
			BeanUtils.copyProperties(wccBrandMerchantAuth, wccBrandMerchantAuthDto);
			
			
			list.add(wccBrandMerchantAuthDto);
		}
		long count = wccBrandMerchantAuthRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
