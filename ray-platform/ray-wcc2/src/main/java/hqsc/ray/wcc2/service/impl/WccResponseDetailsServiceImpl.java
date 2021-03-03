package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccResponseDetailsDto;
import hqsc.ray.wcc2.entity.WccResponseDetails;
import hqsc.ray.wcc2.form.WccResponseDetailsForm;
import hqsc.ray.wcc2.repository.WccResponseDetailsRepository;
import hqsc.ray.wcc2.service.WccResponseDetailsService;
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
public class WccResponseDetailsServiceImpl implements WccResponseDetailsService {
	
	@Autowired
	private WccResponseDetailsRepository wccResponseDetailsRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccResponseDetailsForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccResponseDetailss(WccResponseDetailsForm wccResponseDetailsForm) {
		Specification<WccResponseDetails> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccResponseDetails> wccResponseDetailsList;
		if (wccResponseDetailsForm.getPageNow() == -1) {
			wccResponseDetailsList = wccResponseDetailsRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccResponseDetailsForm.getPageNow() - 1, wccResponseDetailsForm.getPageSize());
			Page<WccResponseDetails> wccResponseDetailsPage = wccResponseDetailsRepository.findAll(specification, pageable);
			wccResponseDetailsList = wccResponseDetailsPage.getContent();
		}
		List<WccResponseDetailsDto> list = new ArrayList<>();
		WccResponseDetailsDto wccResponseDetailsDto;
		for (WccResponseDetails wccResponseDetails : wccResponseDetailsList) {
			wccResponseDetailsDto = new WccResponseDetailsDto();
			BeanUtils.copyProperties(wccResponseDetails, wccResponseDetailsDto);
			
			
			list.add(wccResponseDetailsDto);
		}
		long count = wccResponseDetailsRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
	/**
	 * 新增评论
	 *
	 * @param wccResponseDetailsForm
	 * @return
	 */
	@Override
	public ResultMap saveWccResponseDetails(WccResponseDetailsForm wccResponseDetailsForm) {
		return null;
	}
	
}
