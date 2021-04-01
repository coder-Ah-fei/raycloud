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
import hqsc.ray.wcc.jpa.service.WccCourseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WccCourseServiceImpl implements WccCourseService {
	
	@Autowired
	private WccCourseRepository wccCourseRepository;
	@Autowired
	private RaySysAttachmentRepository raySysAttachmentRepository;
	@Autowired
	private WccCourseResourceRepository wccCourseResourceRepository;
	
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
			jpaWccCourseList = wccCourseRepository.findAll(specification);
			count = Long.valueOf(jpaWccCourseList.size());
		} else {
			Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
			Page<JpaWccCourse> wccCoursePage = wccCourseRepository.findAll(specification, pageable);
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
		long count = wccCourseRepository.count(specification);
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
		
		Optional<JpaWccCourse> wccCourseOptional = wccCourseRepository.findById(wccCourseForm.getWccCourseId());
		if (!wccCourseOptional.isPresent()) {
			return ResultMap.of("课程不存在");
		}
		JpaWccCourse jpaWccCourse = wccCourseOptional.get();
		WccCourseDto wccCourseDto = new WccCourseDto();
		BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
		
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
		
		Optional<JpaWccCourse> jpaWccCourseOptional = wccCourseRepository.findById(wccCourseForm.getId());
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
		
		Optional<JpaWccCourse> wccCourseOptional = wccCourseRepository.findById(wccCourseForm.getId() == null ? 0L : wccCourseForm.getId());
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
		wccCourseRepository.save(jpaWccCourse);
		
		if (resourceListOld != null) {
			for (JpaWccCourseResource resource : resourceListOld) {
				resource.setIsDelete(1);
				resource.setStatus(0);
				wccCourseResourceRepository.save(resource);
			}
		}
		
		return Result.success("保存成功");
	}
	
}
