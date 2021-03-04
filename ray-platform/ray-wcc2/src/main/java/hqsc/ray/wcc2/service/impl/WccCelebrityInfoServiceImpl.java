package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccCelebrityInfoDto;
import hqsc.ray.wcc2.entity.WccCelebrityInfo;
import hqsc.ray.wcc2.form.WccCelebrityInfoForm;
import hqsc.ray.wcc2.repository.WccCelebrityInfoRepository;
import hqsc.ray.wcc2.service.WccCelebrityInfoService;
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
public class WccCelebrityInfoServiceImpl implements WccCelebrityInfoService {
	
	@Autowired
	private WccCelebrityInfoRepository wccCelebrityInfoRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccCelebrityInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccCelebrityInfos(WccCelebrityInfoForm wccCelebrityInfoForm) {
		Map<String, Object> map = new HashMap<>();
		Specification<WccCelebrityInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccCelebrityInfo> wccCelebrityInfoList;
		if (wccCelebrityInfoForm.getPageNow() == -1) {
			wccCelebrityInfoList = wccCelebrityInfoRepository.findAll(specification);
			map.put("count", wccCelebrityInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccCelebrityInfoForm.getPageNow() - 1, wccCelebrityInfoForm.getPageSize());
			Page<WccCelebrityInfo> wccCelebrityInfoPage = wccCelebrityInfoRepository.findAll(specification, pageable);
			wccCelebrityInfoList = wccCelebrityInfoPage.getContent();
			map.put("count", wccCelebrityInfoPage.getTotalElements());
		}
		List<WccCelebrityInfoDto> list = new ArrayList<>();
		WccCelebrityInfoDto wccCelebrityInfoDto;
		for (WccCelebrityInfo wccCelebrityInfo : wccCelebrityInfoList) {
			wccCelebrityInfoDto = new WccCelebrityInfoDto();
			BeanUtils.copyProperties(wccCelebrityInfo, wccCelebrityInfoDto);
			
			list.add(wccCelebrityInfoDto);
		}
		long count = wccCelebrityInfoRepository.count(specification);
		map.put("list", list);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
