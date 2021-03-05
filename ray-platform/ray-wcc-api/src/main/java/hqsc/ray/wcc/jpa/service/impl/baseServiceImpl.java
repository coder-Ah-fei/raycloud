package hqsc.ray.wcc.jpa.service.impl;//package hqsc.ray.wcc.jpa.service.impl;
//
//import hqsc.ray.wcc.jpa.form.baseForm;
//import hqsc.ray.wcc.jpa.repository.baseRepository;
//import hqsc.ray.wcc.jpa.service.baseService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.criteria.Predicate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
///**
// * 描述：
// *
// * @author Administrator
// */
//@Service
//public class baseServiceImpl implements baseService {
//
//	@Autowired
//	private baseRepository baseRepository;
//
//	/**
//	 * 获取数据
//	 *
//	 * @param baseForm
//	 * @return ResultMap
//	 */
//	@Override
//	public ResultMap listbases(baseForm baseForm){
//        Specification<base> specification = (root, criteriaQuery, criteriaBuilder) -> {
////			List<Predicate> pr = new ArrayList< >();
////				if (!StringUtils.empty(articleForm.getSectionName())) {
////					Join<Object, Object> section = root.join("section");
////					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
////				}
//
////			if (!StringUtils.empty(litigationEnvelopeBrandForm.getBrandName())) {
////				pr.add(criteriaBuilder.equal(root.get("brandName").as(String.class), litigationEnvelopeBrandForm.getBrandName()));
////			}
////			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
//
//			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
//			return criteriaQuery.getRestriction();
//		};
//		List<base> baseList;
//		if (baseForm.getPageNow() == -1) {
//			baseList = baseRepository.findAll(specification);
//		} else {
//			Pageable pageable = PageRequest.of(baseForm.getPageNow() - 1, baseForm.getPageSize());
//			Page<base> basePage = baseRepository.findAll(specification, pageable);
//			baseList = basePage.getContent();
//		}
//		List<baseDto> list = new ArrayList< >();
//		baseDto baseDto;
//		for (base base : baseList) {
//            baseDto = new baseDto();
//        	BeanUtils.copyProperties(base, baseDto);
//
//
//            list.add(baseDto);
//		}
//		long count = baseRepository.count(specification);
//		Map<String, Object> map = new HashMap< >();
//		map.put("list", list);
//		map.put("count", count);
//		return new ResultMap< >(ResultMap.SUCCESS_CODE, map);
//	}
//
//}
