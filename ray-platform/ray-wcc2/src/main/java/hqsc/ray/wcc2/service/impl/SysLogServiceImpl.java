package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysLogDto;
import hqsc.ray.wcc2.entity.SysLog;
import hqsc.ray.wcc2.form.SysLogForm;
import hqsc.ray.wcc2.repository.SysLogRepository;
import hqsc.ray.wcc2.service.SysLogService;
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
		Specification<SysLog> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysLog> sysLogList;
		if (sysLogForm.getPageNow() == -1) {
			sysLogList = sysLogRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysLogForm.getPageNow() - 1, sysLogForm.getPageSize());
			Page<SysLog> sysLogPage = sysLogRepository.findAll(specification, pageable);
			sysLogList = sysLogPage.getContent();
		}
		List<SysLogDto> list = new ArrayList<>();
		SysLogDto sysLogDto;
		for (SysLog sysLog : sysLogList) {
			sysLogDto = new SysLogDto();
			BeanUtils.copyProperties(sysLog, sysLogDto);
			
			
			list.add(sysLogDto);
		}
		long count = sysLogRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
