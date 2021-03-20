package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.SysLogDto;
import hqsc.ray.wcc.jpa.entity.JpaSysLog;
import hqsc.ray.wcc.jpa.form.SysLogForm;
import hqsc.ray.wcc.jpa.repository.SysLogRepository;
import hqsc.ray.wcc.jpa.service.SysLogService;
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
public class SysLogServiceImpl implements SysLogService {
	
	@Autowired
	private SysLogRepository sysLogRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysLogForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysLogs(SysLogForm sysLogForm) {
		Specification<JpaSysLog> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysLog> jpaSysLogList;
		if (sysLogForm.getPageNow() == -1) {
			jpaSysLogList = sysLogRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysLogForm.getPageNow() - 1, sysLogForm.getPageSize());
			Page<JpaSysLog> sysLogPage = sysLogRepository.findAll(specification, pageable);
			jpaSysLogList = sysLogPage.getContent();
		}
		List<SysLogDto> list = new ArrayList<>();
		SysLogDto sysLogDto;
		for (JpaSysLog jpaSysLog : jpaSysLogList) {
			sysLogDto = new SysLogDto();
			BeanUtils.copyProperties(jpaSysLog, sysLogDto);
			
			
			list.add(sysLogDto);
		}
		long count = sysLogRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("", map);
	}
	
}
