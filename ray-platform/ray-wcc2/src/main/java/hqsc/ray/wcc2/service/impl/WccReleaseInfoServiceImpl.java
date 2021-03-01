package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccReleaseInfoDto;
import hqsc.ray.wcc2.entity.WccReleaseInfo;
import hqsc.ray.wcc2.form.WccReleaseInfoForm;
import hqsc.ray.wcc2.repository.WccReleaseInfoRepository;
import hqsc.ray.wcc2.service.WccReleaseInfoService;
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
public class WccReleaseInfoServiceImpl implements WccReleaseInfoService {
	
	@Autowired
	private WccReleaseInfoRepository wccReleaseInfoRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccReleaseInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccReleaseInfos(WccReleaseInfoForm wccReleaseInfoForm) {
		Specification<WccReleaseInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccReleaseInfo> wccReleaseInfoList;
		if (wccReleaseInfoForm.getPageNow() == -1) {
			wccReleaseInfoList = wccReleaseInfoRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccReleaseInfoForm.getPageNow() - 1, wccReleaseInfoForm.getPageSize());
			Page<WccReleaseInfo> wccReleaseInfoPage = wccReleaseInfoRepository.findAll(specification, pageable);
			wccReleaseInfoList = wccReleaseInfoPage.getContent();
		}
		List<WccReleaseInfoDto> list = new ArrayList<>();
		WccReleaseInfoDto wccReleaseInfoDto;
		for (WccReleaseInfo wccReleaseInfo : wccReleaseInfoList) {
			wccReleaseInfoDto = new WccReleaseInfoDto();
			BeanUtils.copyProperties(wccReleaseInfo, wccReleaseInfoDto);
			
			
			list.add(wccReleaseInfoDto);
		}
		long count = wccReleaseInfoRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
