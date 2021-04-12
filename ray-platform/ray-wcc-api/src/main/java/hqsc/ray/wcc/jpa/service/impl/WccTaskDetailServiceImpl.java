package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccTaskDetailDto;
import hqsc.ray.wcc.jpa.entity.JpaWccTaskDetail;
import hqsc.ray.wcc.jpa.form.WccTaskDetailForm;
import hqsc.ray.wcc.jpa.repository.WccTaskDetailRepository;
import hqsc.ray.wcc.jpa.service.WccTaskDetailService;
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
		Specification<JpaWccTaskDetail> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccTaskDetail> jpaWccTaskDetailList;
		if (wccTaskDetailForm.getPageNow() == -1) {
			jpaWccTaskDetailList = wccTaskDetailRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccTaskDetailForm.getPageNow() - 1, wccTaskDetailForm.getPageSize());
			Page<JpaWccTaskDetail> wccTaskDetailPage = wccTaskDetailRepository.findAll(specification, pageable);
			jpaWccTaskDetailList = wccTaskDetailPage.getContent();
		}
		List<WccTaskDetailDto> list = new ArrayList<>();
		WccTaskDetailDto wccTaskDetailDto;
		for (JpaWccTaskDetail jpaWccTaskDetail : jpaWccTaskDetailList) {
			wccTaskDetailDto = new WccTaskDetailDto();
			BeanUtils.copyProperties(jpaWccTaskDetail, wccTaskDetailDto);
			
			
			list.add(wccTaskDetailDto);
		}
		long count = wccTaskDetailRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
