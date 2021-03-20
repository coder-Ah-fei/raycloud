package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccPraiseFavoriteDto;
import hqsc.ray.wcc.jpa.entity.JpaWccPraiseFavorite;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.form.WccPraiseFavoriteForm;
import hqsc.ray.wcc.jpa.repository.WccPraiseFavoriteRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.WccPraiseFavoriteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccPraiseFavoriteServiceImpl implements WccPraiseFavoriteService {
	
	@Autowired
	private WccPraiseFavoriteRepository praiseFavoriteRepository;
	@Autowired
	private WccUserRepository userRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccPraiseFavoriteForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccPraiseFavorites(WccPraiseFavoriteForm wccPraiseFavoriteForm) {
		Specification<JpaWccPraiseFavorite> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccPraiseFavorite> jpaWccPraiseFavoriteList;
		if (wccPraiseFavoriteForm.getPageNow() == -1) {
			jpaWccPraiseFavoriteList = praiseFavoriteRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccPraiseFavoriteForm.getPageNow() - 1, wccPraiseFavoriteForm.getPageSize());
			Page<JpaWccPraiseFavorite> wccPraiseFavoritePage = praiseFavoriteRepository.findAll(specification, pageable);
			jpaWccPraiseFavoriteList = wccPraiseFavoritePage.getContent();
		}
		List<WccPraiseFavoriteDto> list = new ArrayList<>();
		WccPraiseFavoriteDto wccPraiseFavoriteDto;
		for (JpaWccPraiseFavorite jpaWccPraiseFavorite : jpaWccPraiseFavoriteList) {
			wccPraiseFavoriteDto = new WccPraiseFavoriteDto();
			BeanUtils.copyProperties(jpaWccPraiseFavorite, wccPraiseFavoriteDto);
			
			
			list.add(wccPraiseFavoriteDto);
		}
		long count = praiseFavoriteRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
	/**
	 * 用户点赞/取消点赞
	 *
	 * @param wccPraiseFavoriteForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> likeOrUnlike(WccPraiseFavoriteForm wccPraiseFavoriteForm) {
		JpaWccPraiseFavorite praiseFavorite;
		List<JpaWccPraiseFavorite> praiseFavorites = praiseFavoriteRepository.findByJpaWccUserIdAndTypeAndPraiseFavoriteTypeAndBelongId(wccPraiseFavoriteForm.getUserId(), wccPraiseFavoriteForm.getType(), wccPraiseFavoriteForm.getPraiseFavoriteType(), wccPraiseFavoriteForm.getBelongId());
		if (praiseFavorites.size() == 0) {
			praiseFavorite = new JpaWccPraiseFavorite();
			if (wccPraiseFavoriteForm.getUserId() == null) {
				return Result.fail("用户未登录或者不存在");
			}
			Optional<JpaWccUser> userOptional = userRepository.findById(wccPraiseFavoriteForm.getUserId());
			if (!userOptional.isPresent()) {
				return Result.fail("用户未登录或者不存在");
			}
			praiseFavorite.setJpaWccUser(userOptional.get());
			praiseFavorite.setType(wccPraiseFavoriteForm.getType());
			praiseFavorite.setPraiseFavoriteType(wccPraiseFavoriteForm.getPraiseFavoriteType());
			praiseFavorite.setBelongId(wccPraiseFavoriteForm.getBelongId());
		} else {
			praiseFavorite = praiseFavorites.get(0);
			praiseFavorite.setStatus(praiseFavorite.getStatus() == 1 ? 0 : 1);
		}
		praiseFavorite = praiseFavoriteRepository.save(praiseFavorite);
		return Result.data(praiseFavorite.getStatus());
	}
	
}
