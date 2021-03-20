package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccUserDto;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.form.WccUserForm;
import hqsc.ray.wcc.jpa.repository.WccUserConcernRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.WccUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
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
public class WccUserServiceImpl implements WccUserService {
	
	@Autowired
	private WccUserRepository wccUserRepository;
	@Autowired
	private WccUserConcernRepository wccUserConcernRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUsers(WccUserForm wccUserForm) {
		Specification<JpaWccUser> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
//				if (!StringUtils.empty(articleForm.getSectionName())) {
//					Join<Object, Object> section = root.join("section");
//					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
//				}
			
			if (wccUserForm.getTalentFlag() != null) {
				pr.add(criteriaBuilder.equal(root.get("talentFlag"), wccUserForm.getTalentFlag()));
			}
			if (wccUserForm.getCelebrityFlag() != null) {
				pr.add(criteriaBuilder.equal(root.get("celebrityFlag"), wccUserForm.getCelebrityFlag()));
			}
			if (wccUserForm.getStarFlag() != null) {
				pr.add(criteriaBuilder.equal(root.get("starFlag"), wccUserForm.getStarFlag()));
			}
			
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccUser> jpaWccUserList;
		if (wccUserForm.getPageNow() == -1) {
			jpaWccUserList = wccUserRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserForm.getPageNow() - 1, wccUserForm.getPageSize());
			Page<JpaWccUser> wccUserPage = wccUserRepository.findAll(specification, pageable);
			jpaWccUserList = wccUserPage.getContent();
		}
		List<WccUserDto> list = new ArrayList<>();
		WccUserDto wccUserDto;
		for (JpaWccUser jpaWccUser : jpaWccUserList) {
			wccUserDto = new WccUserDto();
			BeanUtils.copyProperties(jpaWccUser, wccUserDto);
			wccUserDto.setNickname(StringUtil.toUnicode(jpaWccUser.getNickname()));
			wccUserDto.setJpaSysAttachmentId(jpaWccUser.getJpaSysAttachment() == null ? 0 : jpaWccUser.getJpaSysAttachment().getId());
			wccUserDto.setConcernCount(0L);
			if (wccUserForm.getId() != null) {
				Long aLong = wccUserConcernRepository.countByJpaWccUserIdAndBelongUserId(wccUserForm.getId(), jpaWccUser.getId());
				wccUserDto.setConcernCount(aLong);
			}
			list.add(wccUserDto);
		}
		long count = wccUserRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
}
