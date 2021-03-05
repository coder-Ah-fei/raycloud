package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccReleaseInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaWccReleaseInfo;
import hqsc.ray.wcc.jpa.form.WccReleaseInfoForm;
import hqsc.ray.wcc.jpa.repository.WccReleaseInfoRepository;
import hqsc.ray.wcc.jpa.service.WccReleaseInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
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
public class WccReleaseInfoServiceImpl implements WccReleaseInfoService {
	
	@Autowired
	private WccReleaseInfoRepository wccReleaseInfoRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccReleaseInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccReleaseInfos(WccReleaseInfoForm wccReleaseInfoForm) {
		Specification<JpaWccReleaseInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
			if (wccReleaseInfoForm.getBelongUserId() != null) {
				Join<Object, Object> belongUser = root.join("belongUser");
				pr.add(criteriaBuilder.equal(belongUser.get("id"), wccReleaseInfoForm.getBelongUserId()));
			}
			if (wccReleaseInfoForm.getType() != null) {
				pr.add(criteriaBuilder.equal(root.get("type").as(Long.class), wccReleaseInfoForm.getType()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccReleaseInfo> jpaWccReleaseInfoList;
		if (wccReleaseInfoForm.getPageNow() == -1) {
			jpaWccReleaseInfoList = wccReleaseInfoRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccReleaseInfoForm.getPageNow() - 1, wccReleaseInfoForm.getPageSize());
			Page<JpaWccReleaseInfo> wccReleaseInfoPage = wccReleaseInfoRepository.findAll(specification, pageable);
			jpaWccReleaseInfoList = wccReleaseInfoPage.getContent();
		}
		List<WccReleaseInfoDto> list = new ArrayList<>();
		WccReleaseInfoDto wccReleaseInfoDto;
		for (JpaWccReleaseInfo jpaWccReleaseInfo : jpaWccReleaseInfoList) {
			wccReleaseInfoDto = new WccReleaseInfoDto();
			BeanUtils.copyProperties(jpaWccReleaseInfo, wccReleaseInfoDto);
			
			
			list.add(wccReleaseInfoDto);
		}
		long count = wccReleaseInfoRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
