package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccGoodsInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaWccGoodsInfo;
import hqsc.ray.wcc.jpa.form.WccGoodsInfoForm;
import hqsc.ray.wcc.jpa.repository.WccGoodsInfoRepository;
import hqsc.ray.wcc.jpa.service.WccGoodsInfoService;
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
public class WccGoodsInfoServiceImpl implements WccGoodsInfoService {
	
	@Autowired
	private WccGoodsInfoRepository wccGoodsInfoRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccGoodsInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccGoodsInfos(WccGoodsInfoForm wccGoodsInfoForm) {
		Specification<JpaWccGoodsInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccGoodsInfo> jpaWccGoodsInfoList;
		if (wccGoodsInfoForm.getPageNow() == -1) {
			jpaWccGoodsInfoList = wccGoodsInfoRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccGoodsInfoForm.getPageNow() - 1, wccGoodsInfoForm.getPageSize());
			Page<JpaWccGoodsInfo> wccGoodsInfoPage = wccGoodsInfoRepository.findAll(specification, pageable);
			jpaWccGoodsInfoList = wccGoodsInfoPage.getContent();
		}
		List<WccGoodsInfoDto> list = new ArrayList<>();
		WccGoodsInfoDto wccGoodsInfoDto;
		for (JpaWccGoodsInfo jpaWccGoodsInfo : jpaWccGoodsInfoList) {
			wccGoodsInfoDto = new WccGoodsInfoDto();
			BeanUtils.copyProperties(jpaWccGoodsInfo, wccGoodsInfoDto);
			
			
			list.add(wccGoodsInfoDto);
		}
		long count = wccGoodsInfoRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
