package hqsc.ray.wcc.jpa.service.impl;

import cn.hutool.core.date.DateUtil;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.wcc.jpa.dto.PageMap;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
			if (wccReleaseInfoForm.getBelongCircleId() != null) {
				Join<Object, Object> belongUser = root.join("belongUser");
				Join<Object, Object> userCircle = belongUser.join("userCircleList");
				Join<Object, Object> circleInfo = userCircle.join("jpaWccCircleInfo");
				pr.add(criteriaBuilder.equal(circleInfo.get("id"), wccReleaseInfoForm.getBelongCircleId()));
			}
			if (wccReleaseInfoForm.getType() != null) {
				pr.add(criteriaBuilder.equal(root.get("type").as(Long.class), wccReleaseInfoForm.getType()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccReleaseInfo> jpaWccReleaseInfoList;
		Long count = 0L;
		if (wccReleaseInfoForm.getPageNow() == -1) {
			jpaWccReleaseInfoList = wccReleaseInfoRepository.findAll(specification);
			count = Long.valueOf(jpaWccReleaseInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccReleaseInfoForm.getPageNow() - 1, wccReleaseInfoForm.getPageSize());
			Page<JpaWccReleaseInfo> wccReleaseInfoPage = wccReleaseInfoRepository.findAll(specification, pageable);
			jpaWccReleaseInfoList = wccReleaseInfoPage.getContent();
			count = wccReleaseInfoPage.getTotalElements();
		}
		List<WccReleaseInfoDto> list = new ArrayList<>();
		WccReleaseInfoDto wccReleaseInfoDto;
		for (JpaWccReleaseInfo jpaWccReleaseInfo : jpaWccReleaseInfoList) {
			wccReleaseInfoDto = new WccReleaseInfoDto();
			BeanUtils.copyProperties(jpaWccReleaseInfo, wccReleaseInfoDto);
			wccReleaseInfoDto.setCreationDateTimeStr(jpaWccReleaseInfo.getCreationDate() == null ? "" : DateUtil.formatDateTime(jpaWccReleaseInfo.getCreationDate()));
			if (jpaWccReleaseInfo.getBelongUser() != null) {
				wccReleaseInfoDto.setBelongUserId(jpaWccReleaseInfo.getBelongUser().getId())
						.setBelongUserNickname(StringUtil.toUnicode(jpaWccReleaseInfo.getBelongUser().getNickname()))
						.setWechatHeadPortraitAddress(jpaWccReleaseInfo.getBelongUser().getWechatHeadPortraitAddress())
				;
			}
			list.add(wccReleaseInfoDto);
		}
		
		return new ResultMap<>(ResultMap.SUCCESS_CODE, PageMap.of(count, list));
	}
	
	/**
	 * 发布内容审批
	 *
	 * @param wccReleaseInfoForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> approve(WccReleaseInfoForm wccReleaseInfoForm) {
		Optional<JpaWccReleaseInfo> releaseInfoOptional = wccReleaseInfoRepository.findById(wccReleaseInfoForm.getId());
		if (!releaseInfoOptional.isPresent()) {
			return Result.fail("数据不存在");
		}
		JpaWccReleaseInfo jpaWccReleaseInfo = releaseInfoOptional.get();
		jpaWccReleaseInfo.setApproveStatus(wccReleaseInfoForm.getApproveStatus());
		wccReleaseInfoRepository.save(jpaWccReleaseInfo);
		return Result.success("保存成功");
	}
	
}
