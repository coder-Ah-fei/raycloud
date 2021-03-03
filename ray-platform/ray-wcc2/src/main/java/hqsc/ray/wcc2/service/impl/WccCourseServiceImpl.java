package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccCourseDto;
import hqsc.ray.wcc2.entity.WccCourse;
import hqsc.ray.wcc2.entity.WccCourseResource;
import hqsc.ray.wcc2.entity.WccUser;
import hqsc.ray.wcc2.entity.WccUserPurchasedCourse;
import hqsc.ray.wcc2.form.WccCourseForm;
import hqsc.ray.wcc2.repository.WccCourseRepository;
import hqsc.ray.wcc2.service.WccCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccCourseServiceImpl implements WccCourseService {
	
	@Autowired
	private WccCourseRepository wccCourseRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccCourseForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccCourses(WccCourseForm wccCourseForm) {
		Specification<WccCourse> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
//				if (!StringUtils.empty(articleForm.getSectionName())) {
//					Join<Object, Object> section = root.join("section");
//					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
//				}
			if (wccCourseForm.getIsRecommend() != null) {
				pr.add(criteriaBuilder.equal(root.get("isRecommend").as(Integer.class), wccCourseForm.getIsRecommend()));
			}
			if (wccCourseForm.getIsHot() != null) {
				pr.add(criteriaBuilder.equal(root.get("isHot").as(Integer.class), wccCourseForm.getIsHot()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<WccCourse> wccCourseList;
		if (wccCourseForm.getPageNow() == -1) {
			wccCourseList = wccCourseRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
			Page<WccCourse> wccCoursePage = wccCourseRepository.findAll(specification, pageable);
			wccCourseList = wccCoursePage.getContent();
		}
		List<WccCourseDto> list = new ArrayList<>();
		WccCourseDto wccCourseDto;
		for (WccCourse wccCourse : wccCourseList) {
			wccCourseDto = new WccCourseDto();
			BeanUtils.copyProperties(wccCourse, wccCourseDto);
			
			List<WccCourseResource> resourceList = wccCourse.getResourceList();
			
			List<Long> bannerIdList = new ArrayList<>();
			for (WccCourseResource wccCourseResource : resourceList) {
				if (wccCourseResource.getWccAttachment() == null) {
					continue;
				}
				if (wccCourseResource.getResourceType() == 1) {
					wccCourseDto.setTitlePicId(wccCourseResource.getWccAttachment().getId());
				}
				if (wccCourseResource.getResourceType() == 2) {
					bannerIdList.add(wccCourseResource.getWccAttachment().getId());
				}
			}
			Long[] bannerIds = bannerIdList.toArray(new Long[bannerIdList.size()]);
			Arrays.sort(bannerIds);
			wccCourseDto.setBannerIds(bannerIds);
			list.add(wccCourseDto);
		}
		long count = wccCourseRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
	/**
	 * 获取收藏的课程
	 *
	 * @param wccCourseForm
	 * @return
	 */
	@Override
	public ResultMap listWccCoursesFavorites(WccCourseForm wccCourseForm) {
		wccCourseForm.setPageNow(1);
		Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
		Page<WccCourse> wccCoursePage = wccCourseRepository.listWccCoursesFavorites(wccCourseForm.getUserId(), pageable);
		Map<String, Object> map = new HashMap<>();
//		map.put("list", list);
//		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
	/**
	 * 获取已购买的课程
	 *
	 * @param wccCourseForm
	 * @return
	 */
	@Override
	public ResultMap listWccCoursesBought(WccCourseForm wccCourseForm) {
		Specification<WccCourse> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
			Join<Object, Object> wccPraiseFavoriteJoin = root.join("wccUserPurchasedCourseList");
			Join<WccUserPurchasedCourse, WccUser> userJoin = wccPraiseFavoriteJoin.join("wccUser");
			pr.add(criteriaBuilder.equal(userJoin.get("id"), wccCourseForm.getUserId()));
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<WccCourse> wccCourseList;
		if (wccCourseForm.getPageNow() == -1) {
			wccCourseList = wccCourseRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
			Page<WccCourse> wccCoursePage = wccCourseRepository.findAll(specification, pageable);
			wccCourseList = wccCoursePage.getContent();
		}
		List<WccCourseDto> list = new ArrayList<>();
		WccCourseDto wccCourseDto;
		for (WccCourse wccCourse : wccCourseList) {
			wccCourseDto = new WccCourseDto();
			BeanUtils.copyProperties(wccCourse, wccCourseDto);
			
			List<WccCourseResource> resourceList = wccCourse.getResourceList();
			
			List<Long> bannerIdList = new ArrayList<>();
			for (WccCourseResource wccCourseResource : resourceList) {
				if (wccCourseResource.getWccAttachment() == null) {
					continue;
				}
				if (wccCourseResource.getResourceType() == 1) {
					wccCourseDto.setTitlePicId(wccCourseResource.getWccAttachment().getId());
				}
				if (wccCourseResource.getResourceType() == 2) {
					bannerIdList.add(wccCourseResource.getWccAttachment().getId());
				}
			}
			Long[] bannerIds = bannerIdList.toArray(new Long[bannerIdList.size()]);
			Arrays.sort(bannerIds);
			wccCourseDto.setBannerIds(bannerIds);
			list.add(wccCourseDto);
		}
		long count = wccCourseRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
	/**
	 * 获取课程详细信息
	 *
	 * @param wccCourseForm
	 * @return
	 */
	@Override
	public ResultMap wccCourseDetail(WccCourseForm wccCourseForm) {
		
		Optional<WccCourse> wccCourseOptional = wccCourseRepository.findById(wccCourseForm.getWccCourseId());
		if (!wccCourseOptional.isPresent()) {
			return ResultMap.of("课程不存在");
		}
		WccCourse wccCourse = wccCourseOptional.get();
		WccCourseDto wccCourseDto = new WccCourseDto();
		BeanUtils.copyProperties(wccCourse, wccCourseDto);
		List<WccCourseResource> resourceList = wccCourse.getResourceList();
		List<Long> bannerIdList = new ArrayList<>();
		for (WccCourseResource wccCourseResource : resourceList) {
			if (wccCourseResource.getWccAttachment() == null) {
				continue;
			}
			if (wccCourseResource.getResourceType() == 1) {
				wccCourseDto.setTitlePicId(wccCourseResource.getWccAttachment().getId());
			}
			if (wccCourseResource.getResourceType() == 2) {
				bannerIdList.add(wccCourseResource.getWccAttachment().getId());
			}
		}
		Long[] bannerIds = bannerIdList.toArray(new Long[bannerIdList.size()]);
		Arrays.sort(bannerIds);
		wccCourseDto.setBannerIds(bannerIds);
		
		return ResultMap.of(ResultMap.SUCCESS_CODE, wccCourseDto);
	}
	
}
