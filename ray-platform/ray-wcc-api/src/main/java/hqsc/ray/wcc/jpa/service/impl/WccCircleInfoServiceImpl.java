package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCircleInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaWccCircleInfo;
import hqsc.ray.wcc.jpa.form.WccCircleInfoForm;
import hqsc.ray.wcc.jpa.repository.WccCircleInfoRepository;
import hqsc.ray.wcc.jpa.service.WccCircleInfoService;
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
		Specification<JpaWccCircleInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccCircleInfo> jpaWccCircleInfoList;
		if (wccCircleInfoForm.getPageNow() == -1) {
			jpaWccCircleInfoList = wccCircleInfoRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccCircleInfoForm.getPageNow() - 1, wccCircleInfoForm.getPageSize());
			Page<JpaWccCircleInfo> wccCircleInfoPage = wccCircleInfoRepository.findAll(specification, pageable);
			jpaWccCircleInfoList = wccCircleInfoPage.getContent();
		}
		List<WccCircleInfoDto> list = new ArrayList<>();
		WccCircleInfoDto wccCircleInfoDto;
		for (JpaWccCircleInfo jpaWccCircleInfo : jpaWccCircleInfoList) {
			wccCircleInfoDto = new WccCircleInfoDto();
			BeanUtils.copyProperties(jpaWccCircleInfo, wccCircleInfoDto);
			
			
			list.add(wccCircleInfoDto);
		}
		long count = wccCircleInfoRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
