package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.SysClientDto;
import hqsc.ray.wcc.jpa.entity.JpaSysClient;
import hqsc.ray.wcc.jpa.form.SysClientForm;
import hqsc.ray.wcc.jpa.repository.SysClientRepository;
import hqsc.ray.wcc.jpa.service.SysClientService;
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
		Specification<JpaSysClient> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysClient> jpaSysClientList;
		if (sysClientForm.getPageNow() == -1) {
			jpaSysClientList = sysClientRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(sysClientForm.getPageNow() - 1, sysClientForm.getPageSize());
			Page<JpaSysClient> sysClientPage = sysClientRepository.findAll(specification, pageable);
			jpaSysClientList = sysClientPage.getContent();
		}
		List<SysClientDto> list = new ArrayList<>();
		SysClientDto sysClientDto;
		for (JpaSysClient jpaSysClient : jpaSysClientList) {
			sysClientDto = new SysClientDto();
			BeanUtils.copyProperties(jpaSysClient, sysClientDto);
			
			
			list.add(sysClientDto);
		}
		long count = sysClientRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
