package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccUserConcernDto;
import hqsc.ray.wcc.jpa.dto.WccUserDto;
import hqsc.ray.wcc.jpa.entity.JpaWccUserConcern;
import hqsc.ray.wcc.jpa.form.WccUserConcernForm;
import hqsc.ray.wcc.jpa.repository.WccUserConcernRepository;
import hqsc.ray.wcc.jpa.service.WccUserConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccUserConcernServiceImpl implements WccUserConcernService {
	
	@Autowired
	private WccUserConcernRepository wccUserConcernRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserConcernForm
	 * @return ResultMap
	 */
	@Override
	public PageMap<WccUserDto> listWccUserConcerns(WccUserConcernForm wccUserConcernForm) {
		Specification<JpaWccUserConcern> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
			if (wccUserConcernForm.getUserId() != null) {
				Join<Object, Object> section = root.join("jpaWccUser");
				pr.add(criteriaBuilder.equal(section.get("id"), wccUserConcernForm.getUserId()));
			}
			if (wccUserConcernForm.getStatus() != null) {
				pr.add(criteriaBuilder.equal(root.get("status"), wccUserConcernForm.getStatus()));
			}
//			if (!StringUtils.empty(litigationEnvelopeBrandForm.getBrandName())) {
//				pr.add(criteriaBuilder.equal(root.get("brandName").as(String.class), litigationEnvelopeBrandForm.getBrandName()));
//			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccUserConcern> jpaWccUserConcernList;
		Long count = 0L;
		if (wccUserConcernForm.getPageNow() == -1) {
			jpaWccUserConcernList = wccUserConcernRepository.findAll(specification);
			count = Long.valueOf(jpaWccUserConcernList.size());
		} else {
			Pageable pageable = PageRequest.of(wccUserConcernForm.getPageNow() - 1, wccUserConcernForm.getPageSize());
			Page<JpaWccUserConcern> wccUserConcernPage = wccUserConcernRepository.findAll(specification, pageable);
			jpaWccUserConcernList = wccUserConcernPage.getContent();
			count = wccUserConcernPage.getTotalElements();
		}
		List<WccUserConcernDto> list = new ArrayList<>();
		WccUserConcernDto wccUserConcernDto;
		
		for (JpaWccUserConcern jpaWccUserConcern : jpaWccUserConcernList) {
			wccUserConcernDto = new WccUserConcernDto();
			wccUserConcernDto.setId(jpaWccUserConcern.getId());
			wccUserConcernDto.setJpaWccUserId(jpaWccUserConcern.getJpaWccUser().getId());
			wccUserConcernDto.setBelongUserId(jpaWccUserConcern.getBelongUser().getId());
			wccUserConcernDto.setNickname(jpaWccUserConcern.getBelongUser().getNickname() == null ? "" : StringUtil.toUnicode(jpaWccUserConcern.getBelongUser().getNickname()));
			wccUserConcernDto.setWechatHeadPortraitAddress(jpaWccUserConcern.getBelongUser().getWechatHeadPortraitAddress());
			wccUserConcernDto.setAccessCount(jpaWccUserConcern.getAccessCount());
			list.add(wccUserConcernDto);
		}
		return PageMap.of(count, list);
	}
	
	/**
	 * 获取关注数量
	 *
	 * @param userId
	 * @param belongUserId
	 * @return
	 */
	@Override
	public Long countByJpaWccUserIdAndBelongUserId(Long userId, Long belongUserId) {
		return wccUserConcernRepository.countByJpaWccUserIdAndBelongUserIdAndStatusAndIsDelete(userId, belongUserId, 1, 0);
	}
	
}
