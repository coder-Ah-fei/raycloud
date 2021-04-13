package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccUserPurchasedCourseDto;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.entity.JpaWccUserPurchasedCourse;
import hqsc.ray.wcc.jpa.entity.OrderCourse;
import hqsc.ray.wcc.jpa.form.WccUserPurchasedCourseForm;
import hqsc.ray.wcc.jpa.repository.OrderCourseRepository;
import hqsc.ray.wcc.jpa.repository.WccUserPurchasedCourseRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.UserPurchasedCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
@RequiredArgsConstructor
public class UserPurchasedCourseServiceImpl implements UserPurchasedCourseService {
	
	private final WccUserPurchasedCourseRepository userPurchasedCourseRepository;
	private final WccUserRepository userRepository;
	private final OrderCourseRepository orderCourseRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserPurchasedCourseForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUserPurchasedCourses(WccUserPurchasedCourseForm wccUserPurchasedCourseForm) {
		Specification<JpaWccUserPurchasedCourse> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccUserPurchasedCourse> jpaWccUserPurchasedCourseList;
		if (wccUserPurchasedCourseForm.getPageNow() == -1) {
			jpaWccUserPurchasedCourseList = userPurchasedCourseRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserPurchasedCourseForm.getPageNow() - 1, wccUserPurchasedCourseForm.getPageSize());
			Page<JpaWccUserPurchasedCourse> wccUserPurchasedCoursePage = userPurchasedCourseRepository.findAll(specification, pageable);
			jpaWccUserPurchasedCourseList = wccUserPurchasedCoursePage.getContent();
		}
		List<WccUserPurchasedCourseDto> list = new ArrayList<>();
		WccUserPurchasedCourseDto wccUserPurchasedCourseDto;
		for (JpaWccUserPurchasedCourse jpaWccUserPurchasedCourse : jpaWccUserPurchasedCourseList) {
			wccUserPurchasedCourseDto = new WccUserPurchasedCourseDto();
			BeanUtils.copyProperties(jpaWccUserPurchasedCourse, wccUserPurchasedCourseDto);
			
			
			list.add(wccUserPurchasedCourseDto);
		}
		long count = userPurchasedCourseRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
	/**
	 * 下发课程
	 *
	 * @param jpaWccUser
	 * @param orderCourse
	 */
	@Override
	public void save(JpaWccUser jpaWccUser, OrderCourse orderCourse) {
		Optional<JpaWccUser> userOptional = userRepository.findById(jpaWccUser.getId());
		if (!userOptional.isPresent()) {
			throw new RuntimeException("购买课程成功，但是下发课程失败，原因是用户没登陆或者不存在");
		}
		jpaWccUser = userOptional.get();
		Optional<OrderCourse> orderCourseOptional = orderCourseRepository.findById(orderCourse.getId());
		if (!orderCourseOptional.isPresent()) {
			throw new RuntimeException("购买课程成功，但是下发课程失败，原因是购买课程订单不存在");
		}
		orderCourse = orderCourseOptional.get();
		JpaWccUserPurchasedCourse userPurchasedCourse = new JpaWccUserPurchasedCourse();
		userPurchasedCourse.setJpaWccUser(jpaWccUser);
		userPurchasedCourse.setJpaWccCourse(orderCourse.getJpaWccCourse());
		userPurchasedCourse.setStudyStatus(0);
		userPurchasedCourse.setStatus(1);
		userPurchasedCourse.setIsDelete(0);
		userPurchasedCourseRepository.save(userPurchasedCourse);
	}
	
	/**
	 * 用户是否购买课程
	 *
	 * @param userId
	 * @param courseId
	 * @return
	 */
	@Override
	public Integer isBuyForUser(Long userId, Long courseId) {
		return userPurchasedCourseRepository.countByJpaWccUserIdAndJpaWccCourseIdAndStatusAndIsDelete(userId, courseId, 1, 0);
	}
	
}
