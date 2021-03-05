package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccMoneyReceiptsDto;
import hqsc.ray.wcc.jpa.entity.JpaWccMoneyReceipts;
import hqsc.ray.wcc.jpa.form.WccMoneyReceiptsForm;
import hqsc.ray.wcc.jpa.repository.WccMoneyReceiptsRepository;
import hqsc.ray.wcc.jpa.service.WccMoneyReceiptsService;
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
public class WccMoneyReceiptsServiceImpl implements WccMoneyReceiptsService {
	
	@Autowired
	private WccMoneyReceiptsRepository wccMoneyReceiptsRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccMoneyReceiptsForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccMoneyReceiptss(WccMoneyReceiptsForm wccMoneyReceiptsForm) {
		Specification<JpaWccMoneyReceipts> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccMoneyReceipts> jpaWccMoneyReceiptsList;
		if (wccMoneyReceiptsForm.getPageNow() == -1) {
			jpaWccMoneyReceiptsList = wccMoneyReceiptsRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccMoneyReceiptsForm.getPageNow() - 1, wccMoneyReceiptsForm.getPageSize());
			Page<JpaWccMoneyReceipts> wccMoneyReceiptsPage = wccMoneyReceiptsRepository.findAll(specification, pageable);
			jpaWccMoneyReceiptsList = wccMoneyReceiptsPage.getContent();
		}
		List<WccMoneyReceiptsDto> list = new ArrayList<>();
		WccMoneyReceiptsDto wccMoneyReceiptsDto;
		for (JpaWccMoneyReceipts jpaWccMoneyReceipts : jpaWccMoneyReceiptsList) {
			wccMoneyReceiptsDto = new WccMoneyReceiptsDto();
			BeanUtils.copyProperties(jpaWccMoneyReceipts, wccMoneyReceiptsDto);
			
			
			list.add(wccMoneyReceiptsDto);
		}
		long count = wccMoneyReceiptsRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
