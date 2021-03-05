package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCourseDto;
import hqsc.ray.wcc.jpa.entity.JpaWccCourse;
import hqsc.ray.wcc.jpa.entity.JpaWccCourseResource;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.entity.JpaWccUserPurchasedCourse;
import hqsc.ray.wcc.jpa.form.WccCourseForm;
import hqsc.ray.wcc.jpa.repository.WccCourseRepository;
import hqsc.ray.wcc.jpa.service.WccCourseService;
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
		Specification<JpaWccCourse> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccCourse> jpaWccCourseList;
		if (wccCourseForm.getPageNow() == -1) {
			jpaWccCourseList = wccCourseRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
			Page<JpaWccCourse> wccCoursePage = wccCourseRepository.findAll(specification, pageable);
			jpaWccCourseList = wccCoursePage.getContent();
		}
		List<WccCourseDto> list = new ArrayList<>();
		WccCourseDto wccCourseDto;
		for (JpaWccCourse jpaWccCourse : jpaWccCourseList) {
			wccCourseDto = new WccCourseDto();
			BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
			
			List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
			
			List<Long> bannerIdList = new ArrayList<>();
			for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
				if (jpaWccCourseResource.getWccAttachment() == null) {
					continue;
				}
				if (jpaWccCourseResource.getResourceType() == 1) {
					wccCourseDto.setTitlePicId(jpaWccCourseResource.getWccAttachment().getId());
				}
				if (jpaWccCourseResource.getResourceType() == 2) {
					bannerIdList.add(jpaWccCourseResource.getWccAttachment().getId());
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
		Page<JpaWccCourse> wccCoursePage = wccCourseRepository.listWccCoursesFavorites(wccCourseForm.getUserId(), wccCourseForm.getCourseType(), pageable);
		List<JpaWccCourse> jpaWccCourseList = wccCoursePage.getContent();
		List<WccCourseDto> list = new ArrayList<>();
		WccCourseDto wccCourseDto;
		for (JpaWccCourse jpaWccCourse : jpaWccCourseList) {
			wccCourseDto = new WccCourseDto();
			BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
			
			List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
			
			List<Long> bannerIdList = new ArrayList<>();
			for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
				if (jpaWccCourseResource.getWccAttachment() == null) {
					continue;
				}
				if (jpaWccCourseResource.getResourceType() == 1) {
					wccCourseDto.setTitlePicId(jpaWccCourseResource.getWccAttachment().getId());
				}
				if (jpaWccCourseResource.getResourceType() == 2) {
					bannerIdList.add(jpaWccCourseResource.getWccAttachment().getId());
				}
			}
			Long[] bannerIds = bannerIdList.toArray(new Long[bannerIdList.size()]);
			Arrays.sort(bannerIds);
			wccCourseDto.setBannerIds(bannerIds);
			list.add(wccCourseDto);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", wccCoursePage.getTotalElements());
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
		Specification<JpaWccCourse> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
			if (wccCourseForm.getCourseType() != null) {
				pr.add(criteriaBuilder.equal(root.get("courseType").as(Integer.class), wccCourseForm.getCourseType()));
			}
			
			Join<Object, Object> wccPraiseFavoriteJoin = root.join("wccUserPurchasedCourseList");
			Join<JpaWccUserPurchasedCourse, JpaWccUser> userJoin = wccPraiseFavoriteJoin.join("wccUser");
			pr.add(criteriaBuilder.equal(userJoin.get("id"), wccCourseForm.getUserId()));
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccCourse> jpaWccCourseList;
		if (wccCourseForm.getPageNow() == -1) {
			jpaWccCourseList = wccCourseRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
			Page<JpaWccCourse> wccCoursePage = wccCourseRepository.findAll(specification, pageable);
			jpaWccCourseList = wccCoursePage.getContent();
		}
		List<WccCourseDto> list = new ArrayList<>();
		WccCourseDto wccCourseDto;
		for (JpaWccCourse jpaWccCourse : jpaWccCourseList) {
			wccCourseDto = new WccCourseDto();
			BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
			
			List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
			
			List<Long> bannerIdList = new ArrayList<>();
			for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
				if (jpaWccCourseResource.getWccAttachment() == null) {
					continue;
				}
				if (jpaWccCourseResource.getResourceType() == 1) {
					wccCourseDto.setTitlePicId(jpaWccCourseResource.getWccAttachment().getId());
				}
				if (jpaWccCourseResource.getResourceType() == 2) {
					bannerIdList.add(jpaWccCourseResource.getWccAttachment().getId());
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
		
		Optional<JpaWccCourse> wccCourseOptional = wccCourseRepository.findById(wccCourseForm.getWccCourseId());
		if (!wccCourseOptional.isPresent()) {
			return ResultMap.of("课程不存在");
		}
		JpaWccCourse jpaWccCourse = wccCourseOptional.get();
		WccCourseDto wccCourseDto = new WccCourseDto();
		BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
		List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
		List<Long> bannerIdList = new ArrayList<>();
		for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
			if (jpaWccCourseResource.getWccAttachment() == null) {
				continue;
			}
			if (jpaWccCourseResource.getResourceType() == 1) {
				wccCourseDto.setTitlePicId(jpaWccCourseResource.getWccAttachment().getId());
			}
			if (jpaWccCourseResource.getResourceType() == 2) {
				bannerIdList.add(jpaWccCourseResource.getWccAttachment().getId());
			}
		}
		Long[] bannerIds = bannerIdList.toArray(new Long[bannerIdList.size()]);
		Arrays.sort(bannerIds);
		wccCourseDto.setBannerIds(bannerIds);
		
		return ResultMap.of(ResultMap.SUCCESS_CODE, wccCourseDto);
	}
	
}
