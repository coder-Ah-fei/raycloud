package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCelebrityInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaWccCelebrityInfo;
import hqsc.ray.wcc.jpa.form.WccCelebrityInfoForm;
import hqsc.ray.wcc.jpa.repository.WccCelebrityInfoRepository;
import hqsc.ray.wcc.jpa.service.WccCelebrityInfoService;
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
		Specification<JpaWccCelebrityInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccCelebrityInfo> jpaWccCelebrityInfoList;
		if (wccCelebrityInfoForm.getPageNow() == -1) {
			jpaWccCelebrityInfoList = wccCelebrityInfoRepository.findAll(specification);
			map.put("count", jpaWccCelebrityInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccCelebrityInfoForm.getPageNow() - 1, wccCelebrityInfoForm.getPageSize());
			Page<JpaWccCelebrityInfo> wccCelebrityInfoPage = wccCelebrityInfoRepository.findAll(specification, pageable);
			jpaWccCelebrityInfoList = wccCelebrityInfoPage.getContent();
			map.put("count", wccCelebrityInfoPage.getTotalElements());
		}
		List<WccCelebrityInfoDto> list = new ArrayList<>();
		WccCelebrityInfoDto wccCelebrityInfoDto;
		for (JpaWccCelebrityInfo jpaWccCelebrityInfo : jpaWccCelebrityInfoList) {
			wccCelebrityInfoDto = new WccCelebrityInfoDto();
			BeanUtils.copyProperties(jpaWccCelebrityInfo, wccCelebrityInfoDto);
			
			list.add(wccCelebrityInfoDto);
		}
		long count = wccCelebrityInfoRepository.count(specification);
		map.put("list", list);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
