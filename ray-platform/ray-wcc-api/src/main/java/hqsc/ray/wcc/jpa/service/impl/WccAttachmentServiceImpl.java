package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccAttachmentDto;
import hqsc.ray.wcc.jpa.entity.JpaWccAttachment;
import hqsc.ray.wcc.jpa.form.WccAttachmentForm;
import hqsc.ray.wcc.jpa.repository.WccAttachmentRepository;
import hqsc.ray.wcc.jpa.service.WccAttachmentService;
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
public class WccAttachmentServiceImpl implements WccAttachmentService {
	
	@Autowired
	private WccAttachmentRepository wccAttachmentRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccAttachmentForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccAttachments(WccAttachmentForm wccAttachmentForm) {
		Specification<JpaWccAttachment> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccAttachment> jpaWccAttachmentList;
		if (wccAttachmentForm.getPageNow() == -1) {
			jpaWccAttachmentList = wccAttachmentRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccAttachmentForm.getPageNow() - 1, wccAttachmentForm.getPageSize());
			Page<JpaWccAttachment> wccAttachmentPage = wccAttachmentRepository.findAll(specification, pageable);
			jpaWccAttachmentList = wccAttachmentPage.getContent();
		}
		List<WccAttachmentDto> list = new ArrayList<>();
		WccAttachmentDto wccAttachmentDto;
		for (JpaWccAttachment jpaWccAttachment : jpaWccAttachmentList) {
			wccAttachmentDto = new WccAttachmentDto();
			BeanUtils.copyProperties(jpaWccAttachment, wccAttachmentDto);
			
			
			list.add(wccAttachmentDto);
		}
		long count = wccAttachmentRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
