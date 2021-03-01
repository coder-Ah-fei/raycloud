package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccTaskDetailDto;
import hqsc.ray.wcc2.entity.WccTaskDetail;
import hqsc.ray.wcc2.form.WccTaskDetailForm;
import hqsc.ray.wcc2.repository.WccTaskDetailRepository;
import hqsc.ray.wcc2.service.WccTaskDetailService;
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
public class WccTaskDetailServiceImpl implements WccTaskDetailService {
	
	@Autowired
	private WccTaskDetailRepository wccTaskDetailRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccTaskDetailForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccTaskDetails(WccTaskDetailForm wccTaskDetailForm) {
		Specification<WccTaskDetail> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccTaskDetail> wccTaskDetailList;
		if (wccTaskDetailForm.getPageNow() == -1) {
			wccTaskDetailList = wccTaskDetailRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccTaskDetailForm.getPageNow() - 1, wccTaskDetailForm.getPageSize());
			Page<WccTaskDetail> wccTaskDetailPage = wccTaskDetailRepository.findAll(specification, pageable);
			wccTaskDetailList = wccTaskDetailPage.getContent();
		}
		List<WccTaskDetailDto> list = new ArrayList<>();
		WccTaskDetailDto wccTaskDetailDto;
		for (WccTaskDetail wccTaskDetail : wccTaskDetailList) {
			wccTaskDetailDto = new WccTaskDetailDto();
			BeanUtils.copyProperties(wccTaskDetail, wccTaskDetailDto);
			
			
			list.add(wccTaskDetailDto);
		}
		long count = wccTaskDetailRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
