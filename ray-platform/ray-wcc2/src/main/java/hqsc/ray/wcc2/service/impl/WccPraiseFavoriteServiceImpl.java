package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccPraiseFavoriteDto;
import hqsc.ray.wcc2.entity.WccPraiseFavorite;
import hqsc.ray.wcc2.form.WccPraiseFavoriteForm;
import hqsc.ray.wcc2.repository.WccPraiseFavoriteRepository;
import hqsc.ray.wcc2.service.WccPraiseFavoriteService;
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
public class WccPraiseFavoriteServiceImpl implements WccPraiseFavoriteService {
	
	@Autowired
	private WccPraiseFavoriteRepository wccPraiseFavoriteRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccPraiseFavoriteForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccPraiseFavorites(WccPraiseFavoriteForm wccPraiseFavoriteForm) {
		Specification<WccPraiseFavorite> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccPraiseFavorite> wccPraiseFavoriteList;
		if (wccPraiseFavoriteForm.getPageNow() == -1) {
			wccPraiseFavoriteList = wccPraiseFavoriteRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccPraiseFavoriteForm.getPageNow() - 1, wccPraiseFavoriteForm.getPageSize());
			Page<WccPraiseFavorite> wccPraiseFavoritePage = wccPraiseFavoriteRepository.findAll(specification, pageable);
			wccPraiseFavoriteList = wccPraiseFavoritePage.getContent();
		}
		List<WccPraiseFavoriteDto> list = new ArrayList<>();
		WccPraiseFavoriteDto wccPraiseFavoriteDto;
		for (WccPraiseFavorite wccPraiseFavorite : wccPraiseFavoriteList) {
			wccPraiseFavoriteDto = new WccPraiseFavoriteDto();
			BeanUtils.copyProperties(wccPraiseFavorite, wccPraiseFavoriteDto);
			
			
			list.add(wccPraiseFavoriteDto);
		}
		long count = wccPraiseFavoriteRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
