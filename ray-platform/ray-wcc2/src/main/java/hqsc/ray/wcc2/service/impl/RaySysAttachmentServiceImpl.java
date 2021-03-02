package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.RaySysAttachmentDto;
import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.entity.RaySysAttachment;
import hqsc.ray.wcc2.form.RaySysAttachmentForm;
import hqsc.ray.wcc2.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc2.service.RaySysAttachmentService;
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
public class RaySysAttachmentServiceImpl implements RaySysAttachmentService {
	
	@Autowired
	private RaySysAttachmentRepository raySysAttachmentRepository;
	
	/**
	 * 获取数据
	 *
	 * @param raySysAttachmentForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listRaySysAttachments(RaySysAttachmentForm raySysAttachmentForm) {
		Specification<RaySysAttachment> specification = (root, criteriaQuery, criteriaBuilder) -> {
//			List<Predicate> pr = new ArrayList< >();
//				if (!StringUtils.empty(articleForm.getSectionName())) {
//					Join<Object, Object> section = root.join("section");
//					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
//				}

//			if (!StringUtils.empty(litigationEnvelopeBrandForm.getBrandName())) {
//				pr.add(criteriaBuilder.equal(root.get("brandName").as(String.class), litigationEnvelopeBrandForm.getBrandName()));
//			}
//			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
			return criteriaQuery.getRestriction();
		};
		List<RaySysAttachment> raySysAttachmentList;
		if (raySysAttachmentForm.getPageNow() == -1) {
			raySysAttachmentList = raySysAttachmentRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(raySysAttachmentForm.getPageNow() - 1, raySysAttachmentForm.getPageSize());
			Page<RaySysAttachment> raySysAttachmentPage = raySysAttachmentRepository.findAll(specification, pageable);
			raySysAttachmentList = raySysAttachmentPage.getContent();
		}
		List<RaySysAttachmentDto> list = new ArrayList<>();
		RaySysAttachmentDto raySysAttachmentDto;
		for (RaySysAttachment raySysAttachment : raySysAttachmentList) {
			raySysAttachmentDto = new RaySysAttachmentDto();
			BeanUtils.copyProperties(raySysAttachment, raySysAttachmentDto);
			
			
			list.add(raySysAttachmentDto);
		}
		long count = raySysAttachmentRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
