package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCourseChapterDto;
import hqsc.ray.wcc.jpa.dto.WccCourseDto;
import hqsc.ray.wcc.jpa.entity.*;
import hqsc.ray.wcc.jpa.form.WccCourseForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccCourseRepository;
import hqsc.ray.wcc.jpa.repository.WccCourseResourceRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.UserPurchasedCourseService;
import hqsc.ray.wcc.jpa.service.WccCourseService;
import hqsc.ray.wcc.jpa.service.WccPraiseFavoriteService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
@RequiredArgsConstructor
public class WccCourseServiceImpl implements WccCourseService {
	
	private final WccCourseRepository courseRepository;
	private final RaySysAttachmentRepository raySysAttachmentRepository;
	private final WccCourseResourceRepository wccCourseResourceRepository;
	private final WccPraiseFavoriteService praiseFavoriteService;
	private final UserPurchasedCourseService userPurchasedCourseService;
	private final WccUserRepository userRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccCourseForm
	 * @return ResultMap
	 */
	@Override
	public PageMap<WccCourseDto> listWccCourses(WccCourseForm wccCourseForm) {
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
		Long count = 0L;
		if (wccCourseForm.getPageNow() == -1) {
			jpaWccCourseList = courseRepository.findAll(specification);
			count = Long.valueOf(jpaWccCourseList.size());
		} else {
			Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
			Page<JpaWccCourse> wccCoursePage = courseRepository.findAll(specification, pageable);
			jpaWccCourseList = wccCoursePage.getContent();
			count = wccCoursePage.getTotalElements();
		}
		List<WccCourseDto> list = new ArrayList<>();
		WccCourseDto wccCourseDto;
		for (JpaWccCourse jpaWccCourse : jpaWccCourseList) {
			wccCourseDto = new WccCourseDto();
			BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
			
			List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
			
			List<Long> bannerIdList = new ArrayList<>();
			for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
				if (jpaWccCourseResource.getIsDelete() == 1) {
					continue;
				}
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
		return PageMap.of(count, list);
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
		Page<JpaWccCourse> wccCoursePage = courseRepository.listWccCoursesFavorites(wccCourseForm.getUserId(), wccCourseForm.getCourseType(), pageable);
		List<JpaWccCourse> jpaWccCourseList = wccCoursePage.getContent();
		List<WccCourseDto> list = new ArrayList<>();
		WccCourseDto wccCourseDto;
		for (JpaWccCourse jpaWccCourse : jpaWccCourseList) {
			wccCourseDto = new WccCourseDto();
			BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
			
			List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
			
			List<Long> bannerIdList = new ArrayList<>();
			for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
				if (jpaWccCourseResource.getIsDelete() == 1) {
					continue;
				}
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
		return ResultMap.success("'", map);
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
			jpaWccCourseList = courseRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
			Page<JpaWccCourse> wccCoursePage = courseRepository.findAll(specification, pageable);
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
				if (jpaWccCourseResource.getIsDelete() == 1) {
					continue;
				}
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
		long count = courseRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
	/**
	 * 获取课程详细信息
	 *
	 * @param wccCourseForm
	 * @return
	 */
	@Override
	public ResultMap wccCourseDetail(WccCourseForm wccCourseForm) {
		
		Optional<JpaWccCourse> wccCourseOptional = courseRepository.findById(wccCourseForm.getWccCourseId());
		if (!wccCourseOptional.isPresent()) {
			return ResultMap.of("课程不存在");
		}
		JpaWccCourse jpaWccCourse = wccCourseOptional.get();
		WccCourseDto wccCourseDto = new WccCourseDto();
		BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
		
		if (wccCourseForm.getUserId() != null && wccCourseForm.getUserId() > 0) {
			Integer userFavoritesCount = praiseFavoriteService.countByJpaWccUserIdAndTypeAndPraiseFavoriteTypeAndAndBelongId(wccCourseForm.getUserId(), 1, 5, jpaWccCourse.getId());
			wccCourseDto.setUserFavoritesCount(userFavoritesCount);
			
			Integer buyForUser = userPurchasedCourseService.isBuyForUser(wccCourseForm.getUserId(), jpaWccCourse.getId());
			wccCourseDto.setIsBuyForUser(buyForUser);
		}
		
		List<JpaWccCourseChapter> courseChapterList = jpaWccCourse.getCourseChapterList();
		List<WccCourseChapterDto> courseChapterDtoList = new ArrayList<>();
		WccCourseChapterDto courseChapterDto;
		for (JpaWccCourseChapter courseChapter : courseChapterList) {
			courseChapterDto = new WccCourseChapterDto();
			BeanUtils.copyProperties(courseChapter, courseChapterDto);
			courseChapterDtoList.add(courseChapterDto);
		}
		wccCourseDto.setCourseChapterList(courseChapterDtoList);
		
		
		List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
		List<Long> bannerIdList = new ArrayList<>();
		for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
			if (jpaWccCourseResource.getIsDelete() == 1) {
				continue;
			}
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
	
	/**
	 * 根据id获取课程
	 *
	 * @param wccCourseForm
	 * @return
	 */
	@Override
	public WccCourseDto findById(WccCourseForm wccCourseForm) {
		
		Optional<JpaWccCourse> jpaWccCourseOptional = courseRepository.findById(wccCourseForm.getId());
		if (!jpaWccCourseOptional.isPresent()) {
			throw new RuntimeException("课程不存在");
		}
		JpaWccCourse jpaWccCourse = jpaWccCourseOptional.get();
		WccCourseDto wccCourseDto = new WccCourseDto();
		BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
		
		List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
		
		List<Long> bannerIdList = new ArrayList<>();
		for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
			if (jpaWccCourseResource.getIsDelete() == 1) {
				continue;
			}
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
		return wccCourseDto;
	}
	
	/**
	 * 保存课程,支持新增或修改
	 *
	 * @param wccCourseForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(WccCourseForm wccCourseForm) {
		
		JpaWccCourse jpaWccCourse = null;
		
		Optional<JpaWccCourse> wccCourseOptional = courseRepository.findById(wccCourseForm.getId() == null ? 0L : wccCourseForm.getId());
		List<JpaWccCourseResource> resourceListOld = new ArrayList<>();
		if (wccCourseOptional.isPresent()) {
			jpaWccCourse = wccCourseOptional.get();
			BeanUtils.copyProperties(wccCourseForm, jpaWccCourse);
			List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
			CollectionUtils.addAll(resourceListOld, new String[resourceList.size()]);
			Collections.copy(resourceListOld, resourceList);
			
			
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccCourseForm.getTitlePicId() == null ? 0L : wccCourseForm.getTitlePicId());
			List<JpaWccCourseResource> jpaWccCourseResourceList = new ArrayList<>();
			JpaWccCourseResource resource;
			if (attachmentOptional.isPresent()) {
				resource = new JpaWccCourseResource();
				resource.setJpaWccCourse(jpaWccCourse);
				resource.setWccAttachment(attachmentOptional.get());
				resource.setResourceType(1);
				resource.setSort(0);
				resource.setStatus(1);
				resource.setIsDelete(0);
				jpaWccCourseResourceList.add(resource);
			}
			for (Long bannerId : wccCourseForm.getBannerIds()) {
				attachmentOptional = raySysAttachmentRepository.findById(bannerId);
				if (attachmentOptional.isPresent()) {
					resource = new JpaWccCourseResource();
					resource.setJpaWccCourse(jpaWccCourse);
					resource.setWccAttachment(attachmentOptional.get());
					resource.setResourceType(2);
					resource.setSort(0);
					resource.setStatus(1);
					resource.setIsDelete(0);
					jpaWccCourseResourceList.add(resource);
				}
			}
			jpaWccCourse.setResourceList(jpaWccCourseResourceList);
			
		} else {
			jpaWccCourse = new JpaWccCourse();
			
			BeanUtils.copyProperties(wccCourseForm, jpaWccCourse);
			
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccCourseForm.getTitlePicId() == null ? 0L : wccCourseForm.getTitlePicId());
			List<JpaWccCourseResource> jpaWccCourseResourceList = new ArrayList<>();
			JpaWccCourseResource resource;
			if (attachmentOptional.isPresent()) {
				resource = new JpaWccCourseResource();
				resource.setJpaWccCourse(jpaWccCourse);
				resource.setWccAttachment(attachmentOptional.get());
				resource.setResourceType(1);
				resource.setSort(0);
				resource.setStatus(1);
				resource.setIsDelete(0);
				jpaWccCourseResourceList.add(resource);
			}
			for (Long bannerId : wccCourseForm.getBannerIds()) {
				attachmentOptional = raySysAttachmentRepository.findById(bannerId);
				if (attachmentOptional.isPresent()) {
					resource = new JpaWccCourseResource();
					resource.setJpaWccCourse(jpaWccCourse);
					resource.setWccAttachment(attachmentOptional.get());
					resource.setResourceType(2);
					resource.setSort(0);
					resource.setStatus(1);
					resource.setIsDelete(0);
					jpaWccCourseResourceList.add(resource);
				}
			}
			jpaWccCourse.setResourceList(jpaWccCourseResourceList);
			jpaWccCourse.setStatus(1);
			jpaWccCourse.setIsDelete(0);
		}
		courseRepository.save(jpaWccCourse);
		
		if (resourceListOld != null) {
			for (JpaWccCourseResource resource : resourceListOld) {
				resource.setIsDelete(1);
				resource.setStatus(0);
				wccCourseResourceRepository.save(resource);
			}
		}
		
		return Result.success("保存成功");
	}
	
	/**
	 * 查看用户是否可以学习课程
	 *
	 * @param wccCourseForm
	 * @return
	 */
	@Override
	public Result<Boolean> canStudyCourse(WccCourseForm wccCourseForm) {
		
		Optional<JpaWccUser> userOptional = userRepository.findById(wccCourseForm.getUserId());
		if (!userOptional.isPresent()) {
			return Result.data(false);
		}
		JpaWccUser user = userOptional.get();
		
		Optional<JpaWccCourse> courseOptional = courseRepository.findById(wccCourseForm.getWccCourseId() == null ? 0 : wccCourseForm.getWccCourseId());
		if (!courseOptional.isPresent()) {
			return Result.data(false);
		}
		
		JpaWccCourse course = courseOptional.get();
		
		// 先判断当前课程是不是会员免费
		if (course.getVipIsFree() == 1 && user.getMember() == 1) {
			return Result.data(true);
		}
		
		// 在判断当前用户是否已经购买课程
		Integer buyForUser = userPurchasedCourseService.isBuyForUser(user.getId(), course.getId());
		if (buyForUser > 0) {
			return Result.data(true);
		}
		
		return Result.data(false);
	}
	
}
