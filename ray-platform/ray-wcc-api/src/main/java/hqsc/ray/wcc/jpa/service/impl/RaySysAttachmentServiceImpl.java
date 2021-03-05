package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.RaySysAttachmentDto;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.form.RaySysAttachmentForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.service.RaySysAttachmentService;
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
		Specification<JpaSysAttachment> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysAttachment> jpaSysAttachmentList;
		if (raySysAttachmentForm.getPageNow() == -1) {
			jpaSysAttachmentList = raySysAttachmentRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(raySysAttachmentForm.getPageNow() - 1, raySysAttachmentForm.getPageSize());
			Page<JpaSysAttachment> raySysAttachmentPage = raySysAttachmentRepository.findAll(specification, pageable);
			jpaSysAttachmentList = raySysAttachmentPage.getContent();
		}
		List<RaySysAttachmentDto> list = new ArrayList<>();
		RaySysAttachmentDto raySysAttachmentDto;
		for (JpaSysAttachment jpaSysAttachment : jpaSysAttachmentList) {
			raySysAttachmentDto = new RaySysAttachmentDto();
			BeanUtils.copyProperties(jpaSysAttachment, raySysAttachmentDto);
			
			
			list.add(raySysAttachmentDto);
		}
		long count = raySysAttachmentRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
