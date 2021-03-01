package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccPayLogDto;
import hqsc.ray.wcc2.entity.WccPayLog;
import hqsc.ray.wcc2.form.WccPayLogForm;
import hqsc.ray.wcc2.repository.WccPayLogRepository;
import hqsc.ray.wcc2.service.WccPayLogService;
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
		Specification<WccPayLog> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccPayLog> wccPayLogList;
		if (wccPayLogForm.getPageNow() == -1) {
			wccPayLogList = wccPayLogRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccPayLogForm.getPageNow() - 1, wccPayLogForm.getPageSize());
			Page<WccPayLog> wccPayLogPage = wccPayLogRepository.findAll(specification, pageable);
			wccPayLogList = wccPayLogPage.getContent();
		}
		List<WccPayLogDto> list = new ArrayList<>();
		WccPayLogDto wccPayLogDto;
		for (WccPayLog wccPayLog : wccPayLogList) {
			wccPayLogDto = new WccPayLogDto();
			BeanUtils.copyProperties(wccPayLog, wccPayLogDto);
			
			
			list.add(wccPayLogDto);
		}
		long count = wccPayLogRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
