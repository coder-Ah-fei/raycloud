package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCourseChapterDto;
import hqsc.ray.wcc.jpa.dto.WccCourseDto;
import hqsc.ray.wcc.jpa.entity.*;
import hqsc.ray.wcc.jpa.form.WccCourseChapterForm;
import hqsc.ray.wcc.jpa.form.WccCourseForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccCourseChapterRepository;
import hqsc.ray.wcc.jpa.repository.WccCourseRepository;
import hqsc.ray.wcc.jpa.service.WccCourseChapterService;
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
public class WccCourseChapterServiceImpl implements WccCourseChapterService {
	
	@Autowired
	private WccCourseChapterRepository courseChapterRepository;
	@Autowired
	private RaySysAttachmentRepository attachmentRepository;
	@Autowired
	private WccCourseRepository courseRepository;
	
	/**
	 * 获取数据
	 *
	 * @param courseChapterForm
	 * @return ResultMap
	 */
	@Override
	public PageMap<WccCourseChapterDto> listWccCourseChapters(WccCourseChapterForm courseChapterForm) {
		Specification<JpaWccCourseChapter> specification = (root, criteriaQuery, criteriaBuilder) -> {
//			List<Predicate> pr = new ArrayList<>();
//				if (!StringUtils.empty(articleForm.getSectionName())) {
//					Join<Object, Object> section = root.join("section");
//					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
//				}
//			if (wccCourseForm.getIsRecommend() != null) {
//				pr.add(criteriaBuilder.equal(root.get("isRecommend").as(Integer.class), wccCourseForm.getIsRecommend()));
//			}
//			if (wccCourseForm.getIsHot() != null) {
//				pr.add(criteriaBuilder.equal(root.get("isHot").as(Integer.class), wccCourseForm.getIsHot()));
//			}
//			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccCourseChapter> courseChapterList;
		Long count = 0L;
		if (courseChapterForm.getPageNow() == -1) {
			courseChapterList = courseChapterRepository.findAll(specification);
			count = Long.valueOf(courseChapterList.size());
		} else {
			Pageable pageable = PageRequest.of(courseChapterForm.getPageNow() - 1, courseChapterForm.getPageSize());
			Page<JpaWccCourseChapter> courseChapterPage = courseChapterRepository.findAll(specification, pageable);
			courseChapterList = courseChapterPage.getContent();
			count = courseChapterPage.getTotalElements();
		}
		List<WccCourseChapterDto> list = new ArrayList<>();
		WccCourseChapterDto courseChapterDto;
		
		
		for (JpaWccCourseChapter courseChapter : courseChapterList) {
			courseChapterDto = new WccCourseChapterDto();
			BeanUtils.copyProperties(courseChapter, courseChapterDto);
			courseChapterDto.setId(courseChapter.getId());
			JpaWccCourse course = courseChapter.getJpaWccCourse();
			if (course != null) {
				courseChapterDto.setCourseId(course.getId());
				courseChapterDto.setCourseTitle(course.getCourseTitel());
			}
			JpaSysAttachment attachment = courseChapter.getAttachment();
			if (attachment != null) {
				courseChapterDto.setAttachmentId(attachment.getId());
				courseChapterDto.setVideoScreenshotPath(attachment.getVideoScreenshotPath());
				courseChapterDto.setVideoHlsPath(attachment.getVideoHlsPath());
				courseChapterDto.setFileName(attachment.getFileName());
			}
			courseChapterDto.setChapterTypeStr(courseChapter.getChapterType() == 1 ? "图文" : "视频");
			list.add(courseChapterDto);
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
//		wccCourseForm.setPageNow(1);
//		Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
//		Page<JpaWccCourse> wccCoursePage = courseChapterRepository.listWccCoursesFavorites(wccCourseForm.getUserId(), wccCourseForm.getCourseType(), pageable);
//		List<JpaWccCourse> jpaWccCourseList = wccCoursePage.getContent();
//		List<WccCourseDto> list = new ArrayList<>();
//		WccCourseDto wccCourseDto;
//		for (JpaWccCourse jpaWccCourse : jpaWccCourseList) {
//			wccCourseDto = new WccCourseDto();
//			BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
//
//			List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
//
//			List<Long> bannerIdList = new ArrayList<>();
//			for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
//				if (jpaWccCourseResource.getIsDelete() == 1) {
//					continue;
//				}
//				if (jpaWccCourseResource.getWccAttachment() == null) {
//					continue;
//				}
//				if (jpaWccCourseResource.getResourceType() == 1) {
//					wccCourseDto.setTitlePicId(jpaWccCourseResource.getWccAttachment().getId());
//				}
//				if (jpaWccCourseResource.getResourceType() == 2) {
//					bannerIdList.add(jpaWccCourseResource.getWccAttachment().getId());
//				}
//			}
//			Long[] bannerIds = bannerIdList.toArray(new Long[bannerIdList.size()]);
//			Arrays.sort(bannerIds);
//			wccCourseDto.setBannerIds(bannerIds);
//			list.add(wccCourseDto);
//		}
//		Map<String, Object> map = new HashMap<>();
//		map.put("list", list);
//		map.put("count", wccCoursePage.getTotalElements());
//		return ResultMap.success("'", map);
		return null;
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
			jpaWccCourseList = courseChapterRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccCourseForm.getPageNow() - 1, wccCourseForm.getPageSize());
			Page<JpaWccCourse> wccCoursePage = courseChapterRepository.findAll(specification, pageable);
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
		long count = courseChapterRepository.count(specification);
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

//		Optional<JpaWccCourse> wccCourseOptional = courseChapterRepository.findById(wccCourseForm.getWccCourseId());
//		if (!wccCourseOptional.isPresent()) {
//			return ResultMap.of("课程不存在");
//		}
//		JpaWccCourse jpaWccCourse = wccCourseOptional.get();
//		WccCourseDto wccCourseDto = new WccCourseDto();
//		BeanUtils.copyProperties(jpaWccCourse, wccCourseDto);
//		List<JpaWccCourseResource> resourceList = jpaWccCourse.getResourceList();
//		List<Long> bannerIdList = new ArrayList<>();
//		for (JpaWccCourseResource jpaWccCourseResource : resourceList) {
//			if (jpaWccCourseResource.getIsDelete() == 1) {
//				continue;
//			}
//			if (jpaWccCourseResource.getWccAttachment() == null) {
//				continue;
//			}
//			if (jpaWccCourseResource.getResourceType() == 1) {
//				wccCourseDto.setTitlePicId(jpaWccCourseResource.getWccAttachment().getId());
//			}
//			if (jpaWccCourseResource.getResourceType() == 2) {
//				bannerIdList.add(jpaWccCourseResource.getWccAttachment().getId());
//			}
//		}
//		Long[] bannerIds = bannerIdList.toArray(new Long[bannerIdList.size()]);
//		Arrays.sort(bannerIds);
//		wccCourseDto.setBannerIds(bannerIds);
//
//		return ResultMap.of(ResultMap.SUCCESS_CODE, wccCourseDto);
		return null;
	}
	
	/**
	 * 根据id获取课程
	 *
	 * @param wccCourseForm
	 * @return
	 */
	@Override
	public Result<WccCourseChapterDto> findById(WccCourseChapterForm courseChapterForm) {
		
		Optional<JpaWccCourseChapter> courseChapterOptional = courseChapterRepository.findById(courseChapterForm.getId());
		if (!courseChapterOptional.isPresent()) {
			throw new RuntimeException("课程章节不存在");
		}
		JpaWccCourseChapter courseChapter = courseChapterOptional.get();
		WccCourseChapterDto courseChapterDto = new WccCourseChapterDto();
		BeanUtils.copyProperties(courseChapter, courseChapterDto);
		courseChapterDto.setId(courseChapter.getId());
		JpaWccCourse course = courseChapter.getJpaWccCourse();
		if (course != null) {
			courseChapterDto.setCourseId(course.getId());
			courseChapterDto.setCourseTitle(course.getCourseTitel());
		}
		JpaSysAttachment attachment = courseChapter.getAttachment();
		if (attachment != null) {
			courseChapterDto.setAttachmentId(attachment.getId());
			courseChapterDto.setVideoScreenshotPath(attachment.getVideoScreenshotPath());
			courseChapterDto.setVideoHlsPath(attachment.getVideoHlsPath());
			courseChapterDto.setFileName(attachment.getFileName());
		}
		courseChapterDto.setChapterTypeStr(courseChapter.getChapterType() == 1 ? "图文" : "视频");
		return Result.data(courseChapterDto);
	}
	
	/**
	 * 保存课程,支持新增或修改
	 *
	 * @param courseChapterForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(WccCourseChapterForm courseChapterForm) {
		JpaWccCourseChapter courseChapter = null;
		Optional<JpaWccCourse> courseOptional = courseRepository.findById(courseChapterForm.getCourseId());
		if (!courseOptional.isPresent()) {
			return Result.fail("请选择课程");
		}
		Optional<JpaSysAttachment> attachmentOptional = attachmentRepository.findById(courseChapterForm.getAttachmentId() == null ? 0L : courseChapterForm.getAttachmentId());
		Optional<JpaWccCourseChapter> courseChapterOptional = courseChapterRepository.findById(courseChapterForm.getId() == null ? 0L : courseChapterForm.getId());
		List<JpaWccCourseResource> resourceListOld = new ArrayList<>();
		if (courseChapterOptional.isPresent()) {
			courseChapter = courseChapterOptional.get();
			BeanUtils.copyProperties(courseChapterForm, courseChapter);
			courseChapter.setJpaWccCourse(courseOptional.get())
			
			;
			if (attachmentOptional.isPresent()) {
				courseChapter.setAttachment(attachmentOptional.get());
			}
		} else {
			courseChapter = new JpaWccCourseChapter();
			
			BeanUtils.copyProperties(courseChapterForm, courseChapter);
			courseChapter.setJpaWccCourse(courseOptional.get())
					.setIsDelete(0)
					.setStatus(1);
			if (attachmentOptional.isPresent()) {
				courseChapter.setAttachment(attachmentOptional.get());
			}
		}
		courseChapterRepository.save(courseChapter);
		return Result.success("保存成功");
	}
	
}
