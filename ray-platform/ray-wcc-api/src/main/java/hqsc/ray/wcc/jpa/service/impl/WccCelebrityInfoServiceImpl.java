package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccCelebrityInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccCelebrityInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccMcnInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.form.WccCelebrityInfoForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccCelebrityInfoRepository;
import hqsc.ray.wcc.jpa.repository.WccMcnInfoRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.WccCelebrityInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
@AllArgsConstructor
public class WccCelebrityInfoServiceImpl implements WccCelebrityInfoService {
	
	private final WccCelebrityInfoRepository celebrityInfoRepository;
	private final RaySysAttachmentRepository raySysAttachmentRepository;
	private final WccMcnInfoRepository wccMcnInfoRepository;
	private final WccUserRepository wccUserRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccCelebrityInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccCelebrityInfos(WccCelebrityInfoForm wccCelebrityInfoForm) {
		Map<String, Object> map = new HashMap<>();
		Specification<JpaWccCelebrityInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
			if (!StringUtils.isEmpty(wccCelebrityInfoForm.getPlatform())) {
				pr.add(criteriaBuilder.equal(root.get("platform"), wccCelebrityInfoForm.getPlatform()));
			}
			if (!StringUtils.isEmpty(wccCelebrityInfoForm.getScope())) {
				pr.add(criteriaBuilder.equal(root.get("scope"), wccCelebrityInfoForm.getScope()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			List<Order> orders = new ArrayList<>();
			if (Integer.valueOf(1).equals(wccCelebrityInfoForm.getFansSort())) {
				orders.add(criteriaBuilder.asc(root.get("fans")));
			}
			if (Integer.valueOf(2).equals(wccCelebrityInfoForm.getFansSort())) {
				orders.add(criteriaBuilder.desc(root.get("fans")));
			}
			if (Integer.valueOf(1).equals(wccCelebrityInfoForm.getPriceSort())) {
				orders.add(criteriaBuilder.asc(root.get("livePrice")));
			}
			if (Integer.valueOf(2).equals(wccCelebrityInfoForm.getPriceSort())) {
				orders.add(criteriaBuilder.desc(root.get("livePrice")));
			}
			if (Integer.valueOf(3).equals(wccCelebrityInfoForm.getPriceSort())) {
				orders.add(criteriaBuilder.asc(root.get("video")));
			}
			if (Integer.valueOf(4).equals(wccCelebrityInfoForm.getPriceSort())) {
				orders.add(criteriaBuilder.desc(root.get("video")));
			}
			orders.add(criteriaBuilder.desc(root.get("creationDate")));
			criteriaQuery.orderBy(orders);
			
			return criteriaQuery.getRestriction();
		};
		List<JpaWccCelebrityInfo> jpaWccCelebrityInfoList;
		Long count = 0L;
		if (wccCelebrityInfoForm.getPageNow() == -1) {
			jpaWccCelebrityInfoList = celebrityInfoRepository.findAll(specification);
			count = Long.valueOf(jpaWccCelebrityInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccCelebrityInfoForm.getPageNow() - 1, wccCelebrityInfoForm.getPageSize());
			Page<JpaWccCelebrityInfo> wccCelebrityInfoPage = celebrityInfoRepository.findAll(specification, pageable);
			jpaWccCelebrityInfoList = wccCelebrityInfoPage.getContent();
			count = wccCelebrityInfoPage.getTotalElements();
		}
		List<WccCelebrityInfoDto> list = new ArrayList<>();
		WccCelebrityInfoDto wccCelebrityInfoDto;
		for (JpaWccCelebrityInfo jpaWccCelebrityInfo : jpaWccCelebrityInfoList) {
			wccCelebrityInfoDto = new WccCelebrityInfoDto();
			BeanUtils.copyProperties(jpaWccCelebrityInfo, wccCelebrityInfoDto);
			wccCelebrityInfoDto.setHeadPortraitId(jpaWccCelebrityInfo.getHeadPortrait() == null ? 0L : jpaWccCelebrityInfo.getHeadPortrait().getId());
			if (jpaWccCelebrityInfo.getJpaWccMcnInfo() != null) {
				wccCelebrityInfoDto.setMcnInfoId(jpaWccCelebrityInfo.getJpaWccMcnInfo().getId());
				wccCelebrityInfoDto.setMcnName(jpaWccCelebrityInfo.getJpaWccMcnInfo().getOname());
			}
			list.add(wccCelebrityInfoDto);
		}
		return ResultMap.success("", PageMap.of(count, list));
	}
	
	/**
	 * 红人信息表信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public WccCelebrityInfoDto findById(Long id) {
		Optional<JpaWccCelebrityInfo> celebrityInfoOptional = celebrityInfoRepository.findById(id);
		if (!celebrityInfoOptional.isPresent()) {
			throw new RuntimeException("红人不存在");
		}
		JpaWccCelebrityInfo jpaWccCelebrityInfo = celebrityInfoOptional.get();
		WccCelebrityInfoDto wccCelebrityInfoDto = new WccCelebrityInfoDto();
		BeanUtils.copyProperties(jpaWccCelebrityInfo, wccCelebrityInfoDto);
		wccCelebrityInfoDto.setHeadPortraitId(jpaWccCelebrityInfo.getHeadPortrait() == null ? 0L : jpaWccCelebrityInfo.getHeadPortrait().getId());
		if (jpaWccCelebrityInfo.getJpaWccMcnInfo() != null) {
			wccCelebrityInfoDto.setMcnInfoId(jpaWccCelebrityInfo.getJpaWccMcnInfo().getId());
			wccCelebrityInfoDto.setMcnName(jpaWccCelebrityInfo.getJpaWccMcnInfo().getOname());
		}
		return wccCelebrityInfoDto;
	}
	
	/**
	 * 红人信息表设置
	 *
	 * @param wccCelebrityInfoForm
	 * @return
	 */
	@Override
	public Result<?> save(WccCelebrityInfoForm wccCelebrityInfoForm) {
		JpaWccCelebrityInfo celebrityInfo = null;
		Optional<JpaWccCelebrityInfo> celebrityInfoOptional = celebrityInfoRepository.findById(wccCelebrityInfoForm.getId() == null ? 0L : wccCelebrityInfoForm.getId());
		if (celebrityInfoOptional.isPresent()) {
			celebrityInfo = celebrityInfoOptional.get();
			BeanUtils.copyProperties(wccCelebrityInfoForm, celebrityInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccCelebrityInfoForm.getHeadPortraitId() == null ? 0L : wccCelebrityInfoForm.getHeadPortraitId());
			if (attachmentOptional.isPresent()) {
				celebrityInfo.setHeadPortrait(attachmentOptional.get());
			}
			Optional<JpaWccMcnInfo> mcnInfoOptional = wccMcnInfoRepository.findById(wccCelebrityInfoForm.getMcnInfoId() == null ? 0L : wccCelebrityInfoForm.getMcnInfoId());
			if (mcnInfoOptional.isPresent()) {
				celebrityInfo.setJpaWccMcnInfo(mcnInfoOptional.get());
			}
			Optional<JpaWccUser> userOptional = wccUserRepository.findById(wccCelebrityInfoForm.getBelongUserId() == null ? 0L : wccCelebrityInfoForm.getBelongUserId());
			if (userOptional.isPresent()) {
				celebrityInfo.setBelongUser(userOptional.get());
			}
			
			
		} else {
			celebrityInfo = new JpaWccCelebrityInfo();
			
			BeanUtils.copyProperties(wccCelebrityInfoForm, celebrityInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccCelebrityInfoForm.getHeadPortraitId() == null ? 0L : wccCelebrityInfoForm.getHeadPortraitId());
			if (attachmentOptional.isPresent()) {
				celebrityInfo.setHeadPortrait(attachmentOptional.get());
			}
			Optional<JpaWccMcnInfo> mcnInfoOptional = wccMcnInfoRepository.findById(wccCelebrityInfoForm.getMcnInfoId() == null ? 0L : wccCelebrityInfoForm.getMcnInfoId());
			if (mcnInfoOptional.isPresent()) {
				celebrityInfo.setJpaWccMcnInfo(mcnInfoOptional.get());
			}
			Optional<JpaWccUser> userOptional = wccUserRepository.findById(wccCelebrityInfoForm.getBelongUserId() == null ? 0L : wccCelebrityInfoForm.getBelongUserId());
			if (userOptional.isPresent()) {
				celebrityInfo.setBelongUser(userOptional.get());
			}
			celebrityInfo.setStatus(1);
			celebrityInfo.setIsDelete(0);
		}
		celebrityInfoRepository.save(celebrityInfo);
		return Result.success("保存成功");
	}
	
	/**
	 * 生成红人榜但查询条件的内容
	 *
	 * @return
	 */
	@Override
	public ResultMap findRedSearchData() {
		List<Map<String, Object>> redPlatformList = new ArrayList<>();
		List<Map<String, Object>> redScopeList = new ArrayList<>();
		
		Map<String, Object> map;
		map = new HashMap<>(5);
		map.put("text", "全平台");
		map.put("value", "");
		redPlatformList.add(map);
		map = new HashMap<>(5);
		map.put("text", "全领域");
		map.put("value", "");
		redScopeList.add(map);
		List<JpaWccCelebrityInfo> groupByPlatform = celebrityInfoRepository.findByGroupByPlatform();
		for (JpaWccCelebrityInfo celebrityInfo : groupByPlatform) {
			map = new HashMap<>(5);
			map.put("text", celebrityInfo.getPlatform());
			map.put("value", celebrityInfo.getPlatform());
			redPlatformList.add(map);
		}
		List<JpaWccCelebrityInfo> groupByScope = celebrityInfoRepository.findByGroupByScope();
		for (JpaWccCelebrityInfo celebrityInfo : groupByScope) {
			map = new HashMap<>(5);
			map.put("text", celebrityInfo.getScope());
			map.put("value", celebrityInfo.getScope());
			redScopeList.add(map);
		}
		map = new HashMap<>(5);
		map.put("redPlatformList", redPlatformList);
		map.put("redScopeList", redScopeList);
		return ResultMap.success("", map);
	}
	
}
