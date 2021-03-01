package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccCircleInfoDto;
import hqsc.ray.wcc2.entity.WccCircleInfo;
import hqsc.ray.wcc2.form.WccCircleInfoForm;
import hqsc.ray.wcc2.repository.WccCircleInfoRepository;
import hqsc.ray.wcc2.service.WccCircleInfoService;
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
public class WccCircleInfoServiceImpl implements WccCircleInfoService {
	
	@Autowired
	private WccCircleInfoRepository wccCircleInfoRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccCircleInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccCircleInfos(WccCircleInfoForm wccCircleInfoForm) {
		Specification<WccCircleInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccCircleInfo> wccCircleInfoList;
		if (wccCircleInfoForm.getPageNow() == -1) {
			wccCircleInfoList = wccCircleInfoRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccCircleInfoForm.getPageNow() - 1, wccCircleInfoForm.getPageSize());
			Page<WccCircleInfo> wccCircleInfoPage = wccCircleInfoRepository.findAll(specification, pageable);
			wccCircleInfoList = wccCircleInfoPage.getContent();
		}
		List<WccCircleInfoDto> list = new ArrayList<>();
		WccCircleInfoDto wccCircleInfoDto;
		for (WccCircleInfo wccCircleInfo : wccCircleInfoList) {
			wccCircleInfoDto = new WccCircleInfoDto();
			BeanUtils.copyProperties(wccCircleInfo, wccCircleInfoDto);
			
			
			list.add(wccCircleInfoDto);
		}
		long count = wccCircleInfoRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
