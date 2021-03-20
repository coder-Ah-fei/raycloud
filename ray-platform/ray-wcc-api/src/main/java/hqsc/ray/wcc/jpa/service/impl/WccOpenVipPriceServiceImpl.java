package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccOpenVipPriceDto;
import hqsc.ray.wcc.jpa.entity.JpaWccOpenVipPrice;
import hqsc.ray.wcc.jpa.form.WccOpenVipPriceForm;
import hqsc.ray.wcc.jpa.repository.WccOpenVipPriceRepository;
import hqsc.ray.wcc.jpa.service.WccOpenVipPriceService;
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
public class WccOpenVipPriceServiceImpl implements WccOpenVipPriceService {
	
	@Autowired
	private WccOpenVipPriceRepository wccOpenVipPriceRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccOpenVipPriceForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccOpenVipPrices(WccOpenVipPriceForm wccOpenVipPriceForm) {
		Specification<JpaWccOpenVipPrice> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccOpenVipPrice> jpaWccOpenVipPriceList;
		if (wccOpenVipPriceForm.getPageNow() == -1) {
			jpaWccOpenVipPriceList = wccOpenVipPriceRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccOpenVipPriceForm.getPageNow() - 1, wccOpenVipPriceForm.getPageSize());
			Page<JpaWccOpenVipPrice> wccOpenVipPricePage = wccOpenVipPriceRepository.findAll(specification, pageable);
			jpaWccOpenVipPriceList = wccOpenVipPricePage.getContent();
		}
		List<WccOpenVipPriceDto> list = new ArrayList<>();
		WccOpenVipPriceDto wccOpenVipPriceDto;
		for (JpaWccOpenVipPrice jpaWccOpenVipPrice : jpaWccOpenVipPriceList) {
			wccOpenVipPriceDto = new WccOpenVipPriceDto();
			BeanUtils.copyProperties(jpaWccOpenVipPrice, wccOpenVipPriceDto);
			
			
			list.add(wccOpenVipPriceDto);
		}
		long count = wccOpenVipPriceRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
