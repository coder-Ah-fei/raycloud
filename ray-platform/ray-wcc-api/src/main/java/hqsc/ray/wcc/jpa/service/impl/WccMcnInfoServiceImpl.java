package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccMcnInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccMcnInfo;
import hqsc.ray.wcc.jpa.form.WccMcnInfoForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccMcnInfoRepository;
import hqsc.ray.wcc.jpa.service.WccMcnInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class WccMcnInfoServiceImpl implements WccMcnInfoService {
	
	private final WccMcnInfoRepository mcnInfoRepository;
	private final RaySysAttachmentRepository raySysAttachmentRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccMcnInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccMcnInfos(WccMcnInfoForm wccMcnInfoForm) {
		Specification<JpaWccMcnInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
			if (!StringUtils.isEmpty(wccMcnInfoForm.getAddress())) {
				pr.add(criteriaBuilder.equal(root.get("address"), wccMcnInfoForm.getAddress()));
			}
			if (!StringUtils.isEmpty(wccMcnInfoForm.getField())) {
				pr.add(criteriaBuilder.equal(root.get("field"), wccMcnInfoForm.getField()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			List<Order> orders = new ArrayList<>();
			if (Integer.valueOf(1).equals(wccMcnInfoForm.getCelebrityCountSort())) {
				orders.add(criteriaBuilder.asc(root.get("celebrityCount")));
			}
			if (Integer.valueOf(2).equals(wccMcnInfoForm.getCelebrityCountSort())) {
				orders.add(criteriaBuilder.desc(root.get("celebrityCount")));
			}
			orders.add(criteriaBuilder.desc(root.get("creationDate")));
			criteriaQuery.orderBy(orders);
			return criteriaQuery.getRestriction();
		};
		List<JpaWccMcnInfo> jpaWccMcnInfoList;
		Long count;
		if (wccMcnInfoForm.getPageNow() == -1) {
			jpaWccMcnInfoList = mcnInfoRepository.findAll(specification);
			count = Long.valueOf(jpaWccMcnInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccMcnInfoForm.getPageNow() - 1, wccMcnInfoForm.getPageSize());
			Page<JpaWccMcnInfo> wccMcnInfoPage = mcnInfoRepository.findAll(specification, pageable);
			jpaWccMcnInfoList = wccMcnInfoPage.getContent();
			count = wccMcnInfoPage.getTotalElements();
		}
		List<WccMcnInfoDto> list = new ArrayList<>();
		WccMcnInfoDto wccMcnInfoDto;
		for (JpaWccMcnInfo jpaWccMcnInfo : jpaWccMcnInfoList) {
			wccMcnInfoDto = new WccMcnInfoDto();
			BeanUtils.copyProperties(jpaWccMcnInfo, wccMcnInfoDto);
			wccMcnInfoDto.setIconId(jpaWccMcnInfo.getIcon() == null ? null : jpaWccMcnInfo.getIcon().getId());
			
			list.add(wccMcnInfoDto);
		}
		return ResultMap.success("", PageMap.of(count, list));
	}
	
	/**
	 * mcn机构信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public WccMcnInfoDto findById(Long id) {
		Optional<JpaWccMcnInfo> mcnInfoOptional = mcnInfoRepository.findById(id);
		if (!mcnInfoOptional.isPresent()) {
			throw new RuntimeException("MCN机构不存在");
		}
		JpaWccMcnInfo jpaWccMcnInfo = mcnInfoOptional.get();
		WccMcnInfoDto wccMcnInfoDto = new WccMcnInfoDto();
		BeanUtils.copyProperties(jpaWccMcnInfo, wccMcnInfoDto);
		wccMcnInfoDto.setIconId(jpaWccMcnInfo.getIcon() == null ? null : jpaWccMcnInfo.getIcon().getId());
		return wccMcnInfoDto;
	}
	
	/**
	 * mcn机构信息设置
	 *
	 * @param wccMcnInfoForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(WccMcnInfoForm wccMcnInfoForm) {
		JpaWccMcnInfo jpaWccMcnInfo = null;
		Optional<JpaWccMcnInfo> mcnInfoOptional = mcnInfoRepository.findById(wccMcnInfoForm.getId() == null ? 0L : wccMcnInfoForm.getId());
		if (mcnInfoOptional.isPresent()) {
			jpaWccMcnInfo = mcnInfoOptional.get();
			BeanUtils.copyProperties(wccMcnInfoForm, jpaWccMcnInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccMcnInfoForm.getIconId() == null ? 0L : wccMcnInfoForm.getIconId());
			if (attachmentOptional.isPresent()) {
				jpaWccMcnInfo.setIcon(attachmentOptional.get());
			}
			
		} else {
			jpaWccMcnInfo = new JpaWccMcnInfo();
			BeanUtils.copyProperties(wccMcnInfoForm, jpaWccMcnInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccMcnInfoForm.getIconId() == null ? 0L : wccMcnInfoForm.getIconId());
			if (attachmentOptional.isPresent()) {
				jpaWccMcnInfo.setIcon(attachmentOptional.get());
			}
			jpaWccMcnInfo.setStatus(1);
			jpaWccMcnInfo.setIsDelete(0);
		}
		mcnInfoRepository.save(jpaWccMcnInfo);
		return Result.success("保存成功");
	}
	
	/**
	 * 生成MCN榜单查询条件的内容
	 *
	 * @return
	 */
	@Override
	public ResultMap findMcnSearchData() {
		List<Map<String, Object>> mcnAddressList = new ArrayList<>();
		List<Map<String, Object>> mcnFieldList = new ArrayList<>();
		
		Map<String, Object> map;
		map = new HashMap<>(5);
		map.put("text", "全地区");
		map.put("value", "");
		mcnAddressList.add(map);
		map.put("text", "全领域");
		map.put("value", "");
		mcnFieldList.add(map);
		
		List<JpaWccMcnInfo> byGroupByAddress = mcnInfoRepository.findByGroupByAddress();
		for (JpaWccMcnInfo mcnInfo : byGroupByAddress) {
			map = new HashMap<>(5);
			map.put("text", mcnInfo.getAddress());
			map.put("value", mcnInfo.getAddress());
			mcnAddressList.add(map);
		}
		List<JpaWccMcnInfo> byGroupByFieldd = mcnInfoRepository.findByGroupByFieldd();
		for (JpaWccMcnInfo mcnInfo : byGroupByFieldd) {
			map = new HashMap<>(5);
			map.put("text", mcnInfo.getField());
			map.put("value", mcnInfo.getField());
			mcnFieldList.add(map);
		}
		map = new HashMap<>(5);
		map.put("mcnAddressList", mcnAddressList);
		map.put("mcnFieldList", mcnFieldList);
		
		return ResultMap.success("", map);
	}
	
}
