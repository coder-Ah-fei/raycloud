package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccPayLogDto;
import hqsc.ray.wcc.jpa.entity.JpaWccPayLog;
import hqsc.ray.wcc.jpa.form.WccPayLogForm;
import hqsc.ray.wcc.jpa.repository.WccPayLogRepository;
import hqsc.ray.wcc.jpa.service.WccPayLogService;
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
public class WccPayLogServiceImpl implements WccPayLogService {
	
	@Autowired
	private WccPayLogRepository wccPayLogRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccPayLogForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccPayLogs(WccPayLogForm wccPayLogForm) {
		Specification<JpaWccPayLog> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccPayLog> jpaWccPayLogList;
		if (wccPayLogForm.getPageNow() == -1) {
			jpaWccPayLogList = wccPayLogRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccPayLogForm.getPageNow() - 1, wccPayLogForm.getPageSize());
			Page<JpaWccPayLog> wccPayLogPage = wccPayLogRepository.findAll(specification, pageable);
			jpaWccPayLogList = wccPayLogPage.getContent();
		}
		List<WccPayLogDto> list = new ArrayList<>();
		WccPayLogDto wccPayLogDto;
		for (JpaWccPayLog jpaWccPayLog : jpaWccPayLogList) {
			wccPayLogDto = new WccPayLogDto();
			BeanUtils.copyProperties(jpaWccPayLog, wccPayLogDto);
			
			
			list.add(wccPayLogDto);
		}
		long count = wccPayLogRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
