package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccMoneyReceiptsDto;
import hqsc.ray.wcc2.entity.WccMoneyReceipts;
import hqsc.ray.wcc2.form.WccMoneyReceiptsForm;
import hqsc.ray.wcc2.repository.WccMoneyReceiptsRepository;
import hqsc.ray.wcc2.service.WccMoneyReceiptsService;
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
		Specification<WccMoneyReceipts> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccMoneyReceipts> wccMoneyReceiptsList;
		if (wccMoneyReceiptsForm.getPageNow() == -1) {
			wccMoneyReceiptsList = wccMoneyReceiptsRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccMoneyReceiptsForm.getPageNow() - 1, wccMoneyReceiptsForm.getPageSize());
			Page<WccMoneyReceipts> wccMoneyReceiptsPage = wccMoneyReceiptsRepository.findAll(specification, pageable);
			wccMoneyReceiptsList = wccMoneyReceiptsPage.getContent();
		}
		List<WccMoneyReceiptsDto> list = new ArrayList<>();
		WccMoneyReceiptsDto wccMoneyReceiptsDto;
		for (WccMoneyReceipts wccMoneyReceipts : wccMoneyReceiptsList) {
			wccMoneyReceiptsDto = new WccMoneyReceiptsDto();
			BeanUtils.copyProperties(wccMoneyReceipts, wccMoneyReceiptsDto);
			
			
			list.add(wccMoneyReceiptsDto);
		}
		long count = wccMoneyReceiptsRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
