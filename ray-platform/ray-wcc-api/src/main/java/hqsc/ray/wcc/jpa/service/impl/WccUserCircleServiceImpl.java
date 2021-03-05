package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccUserCircleDto;
import hqsc.ray.wcc.jpa.entity.JpaWccUserCircle;
import hqsc.ray.wcc.jpa.form.WccUserCircleForm;
import hqsc.ray.wcc.jpa.repository.WccUserCircleRepository;
import hqsc.ray.wcc.jpa.service.WccUserCircleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class WccUserCircleServiceImpl implements WccUserCircleService {
	
	@Autowired
	private WccUserCircleRepository wccUserCircleRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserCircleForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUserCircles(WccUserCircleForm wccUserCircleForm) {
		Specification<JpaWccUserCircle> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccUserCircle> jpaWccUserCircleList;
		if (wccUserCircleForm.getPageNow() == -1) {
			jpaWccUserCircleList = wccUserCircleRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserCircleForm.getPageNow() - 1, wccUserCircleForm.getPageSize());
			Page<JpaWccUserCircle> wccUserCirclePage = wccUserCircleRepository.findAll(specification, pageable);
			jpaWccUserCircleList = wccUserCirclePage.getContent();
		}
		List<WccUserCircleDto> list = new ArrayList<>();
		WccUserCircleDto wccUserCircleDto;
		for (JpaWccUserCircle jpaWccUserCircle : jpaWccUserCircleList) {
			wccUserCircleDto = new WccUserCircleDto();
			BeanUtils.copyProperties(jpaWccUserCircle, wccUserCircleDto);
			
			
			list.add(wccUserCircleDto);
		}
		long count = wccUserCircleRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
	/**
	 * 取消加入圈子
	 *
	 * @param userId
	 * @param circleId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result cancelJoinCircle(Long userId, Long circleId) {
		List<JpaWccUserCircle> wccUserCircleList = wccUserCircleRepository.findByJpaWccUserIdAndJpaWccCircleInfoId(userId, circleId);
		if (wccUserCircleList.size() != 1) {
			return Result.fail("没找到关联数据");
		}
		JpaWccUserCircle jpaWccUserCircle = wccUserCircleList.get(0);
		jpaWccUserCircle.setStatus(0);
		wccUserCircleRepository.save(jpaWccUserCircle);
		return Result.success("成功退出圈子");
	}
	
}
