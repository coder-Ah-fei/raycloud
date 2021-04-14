package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.util.DateUtil;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccReleaseInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccReleaseInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.form.WccReleaseInfoForm;
import hqsc.ray.wcc.jpa.repository.WccReleaseInfoRepository;
import hqsc.ray.wcc.jpa.repository.WccResponseDetailsRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.WccPraiseFavoriteService;
import hqsc.ray.wcc.jpa.service.WccReleaseInfoService;
import hqsc.ray.wcc.jpa.service.WccUserConcernService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@RequiredArgsConstructor
public class WccReleaseInfoServiceImpl implements WccReleaseInfoService {
	
	private final WccReleaseInfoRepository releaseInfoRepository;
	private final WccPraiseFavoriteService praiseFavoriteService;
	private final WccResponseDetailsRepository responseDetailsRepository;
	private final WccUserConcernService userConcernService;
	private final WccUserRepository userRepository;
	
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
				Join<Object, Object> circleInfo = root.join("belongCircle");
				pr.add(criteriaBuilder.equal(circleInfo.get("id"), wccReleaseInfoForm.getBelongCircleId()));
			}
			if (wccReleaseInfoForm.getType() != null) {
				pr.add(criteriaBuilder.equal(root.get("type").as(Long.class), wccReleaseInfoForm.getType()));
			}
			if (wccReleaseInfoForm.getStatus() != null) {
				pr.add(criteriaBuilder.equal(root.get("status"), wccReleaseInfoForm.getStatus()));
			}
			if (wccReleaseInfoForm.getIsDelete() != null) {
				pr.add(criteriaBuilder.equal(root.get("isDelete"), wccReleaseInfoForm.getIsDelete()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccReleaseInfo> jpaWccReleaseInfoList;
		Long count = 0L;
		if (wccReleaseInfoForm.getPageNow() == -1) {
			jpaWccReleaseInfoList = releaseInfoRepository.findAll(specification);
			count = Long.valueOf(jpaWccReleaseInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccReleaseInfoForm.getPageNow() - 1, wccReleaseInfoForm.getPageSize());
			Page<JpaWccReleaseInfo> wccReleaseInfoPage = releaseInfoRepository.findAll(specification, pageable);
			jpaWccReleaseInfoList = wccReleaseInfoPage.getContent();
			count = wccReleaseInfoPage.getTotalElements();
		}
		List<WccReleaseInfoDto> list = new ArrayList<>();
		WccReleaseInfoDto wccReleaseInfoDto;
		for (JpaWccReleaseInfo jpaWccReleaseInfo : jpaWccReleaseInfoList) {
			wccReleaseInfoDto = new WccReleaseInfoDto();
			BeanUtils.copyProperties(jpaWccReleaseInfo, wccReleaseInfoDto);
			wccReleaseInfoDto.setCreationDateTimeStr(jpaWccReleaseInfo.getCreationDate() == null ? "" : DateUtil.simpleFormatWithYear(jpaWccReleaseInfo.getCreationDate()));
			if (jpaWccReleaseInfo.getBelongUser() != null) {
				wccReleaseInfoDto.setBelongUserId(jpaWccReleaseInfo.getBelongUser().getId())
						.setBelongUserNickname(StringUtil.toUnicode(jpaWccReleaseInfo.getBelongUser().getNickname()))
						.setWechatHeadPortraitAddress(jpaWccReleaseInfo.getBelongUser().getWechatHeadPortraitAddress())
				;
			}
			JpaSysAttachment sysAttachment = jpaWccReleaseInfo.getJpaWccAttachment();
			if (sysAttachment != null) {
				wccReleaseInfoDto.setVideoHlsPath(sysAttachment.getVideoHlsPath())
						.setVideoScreenshotPath(sysAttachment.getVideoScreenshotPath())
						.setSysAttachmentId(sysAttachment.getId());
			}
			
			// 获取点赞数量
			Integer praiseCount = praiseFavoriteService.countByTypeAndPraiseFavoriteTypeAndAndBelongId(0, jpaWccReleaseInfo.getType().intValue(), jpaWccReleaseInfo.getId());
			wccReleaseInfoDto.setPraiseCount(praiseCount);
			// 获取评论数量
			wccReleaseInfoDto.setCommentCount(responseDetailsRepository.countByBelongIdAndBelongTypeAndStatusAndIsDelete(jpaWccReleaseInfo.getId(), 0, 1, 0));
			
			// 当前登录用户是否收藏
			wccReleaseInfoDto.setFavoritesCount(0);
			// 当前登录用户是否关注
			wccReleaseInfoDto.setConcernCount(0);
			if (wccReleaseInfoForm.getUserId() != null) {
				wccReleaseInfoDto.setFavoritesCount(praiseFavoriteService.countByJpaWccUserIdAndTypeAndPraiseFavoriteTypeAndAndBelongId(wccReleaseInfoForm.getUserId(), 1, jpaWccReleaseInfo.getType().intValue(), jpaWccReleaseInfo.getId()));
				wccReleaseInfoDto.setConcernCount(userConcernService.countByJpaWccUserIdAndBelongUserId(wccReleaseInfoForm.getUserId(), jpaWccReleaseInfo.getBelongUser().getId()).intValue());
			}
			
			if (jpaWccReleaseInfo.getType() == 2) {
				List<String> imgStrs = StringUtil.getImgStr(jpaWccReleaseInfo.getContent());
				if (imgStrs.size() > 0) {
					wccReleaseInfoDto.setArticleImgUrl(imgStrs.get(0));
				}
				if (jpaWccReleaseInfo.getContent() != null) {
					wccReleaseInfoDto.setContent(StringUtil.trimHtml(jpaWccReleaseInfo.getContent(), 100));
				}
			}
			
			list.add(wccReleaseInfoDto);
		}
		
		return ResultMap.success("", PageMap.of(count, list));
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
		Optional<JpaWccReleaseInfo> releaseInfoOptional = releaseInfoRepository.findById(wccReleaseInfoForm.getId());
		if (!releaseInfoOptional.isPresent()) {
			return Result.fail("数据不存在");
		}
		JpaWccReleaseInfo jpaWccReleaseInfo = releaseInfoOptional.get();
		jpaWccReleaseInfo.setApproveStatus(wccReleaseInfoForm.getApproveStatus());
		releaseInfoRepository.save(jpaWccReleaseInfo);
		return Result.success("保存成功");
	}
	
	/**
	 * 保存新增/编辑的发布内容
	 *
	 * @param releaseInfoForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> saveOrUpdate(WccReleaseInfoForm releaseInfoForm) {
		Optional<JpaWccUser> userOptional = userRepository.findById(releaseInfoForm.getUserId());
		if (!userOptional.isPresent()) {
			return Result.condition(false);
		}
		Optional<JpaWccReleaseInfo> releaseInfoOptional = releaseInfoRepository.findById(releaseInfoForm.getId() == null ? 0 : releaseInfoForm.getId());
		JpaWccReleaseInfo releaseInfo = new JpaWccReleaseInfo();
		if (releaseInfoOptional.isPresent()) {
			releaseInfo = releaseInfoOptional.get();
		}
		
		releaseInfo.setTitel(releaseInfoForm.getTitel())
				.setBelongUser(userOptional.get())
				.setContent(releaseInfoForm.getContent())
				.setType(releaseInfoForm.getType())
				.setApproveStatus(0)
				.setStatus(1)
				.setIsDelete(0)
		;
		releaseInfoRepository.save(releaseInfo);
		
		return Result.condition(true);
	}
	
}
