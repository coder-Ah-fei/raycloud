package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.SysClientDto;
import hqsc.ray.wcc2.entity.SysClient;
import hqsc.ray.wcc2.form.SysClientForm;
import hqsc.ray.wcc2.repository.SysClientRepository;
import hqsc.ray.wcc2.service.SysClientService;
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
public class SysClientServiceImpl implements SysClientService {
	
	@Autowired
	private SysClientRepository sysClientRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysClientForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listSysClients(SysClientForm sysClientForm) {
		Specification<SysClient> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<SysClient> sysClientList;
		if (sysClientForm.getPageNow() == -1) {
			sysClientList = sysClientRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysClientForm.getPageNow() - 1, sysClientForm.getPageSize());
			Page<SysClient> sysClientPage = sysClientRepository.findAll(specification, pageable);
			sysClientList = sysClientPage.getContent();
		}
		List<SysClientDto> list = new ArrayList<>();
		SysClientDto sysClientDto;
		for (SysClient sysClient : sysClientList) {
			sysClientDto = new SysClientDto();
			BeanUtils.copyProperties(sysClient, sysClientDto);
			
			
			list.add(sysClientDto);
		}
		long count = sysClientRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
