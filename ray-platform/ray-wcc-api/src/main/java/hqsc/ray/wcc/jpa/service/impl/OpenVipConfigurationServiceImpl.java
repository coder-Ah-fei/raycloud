package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.OpenVipConfigurationDto;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.entity.OpenVipConfiguration;
import hqsc.ray.wcc.jpa.form.OpenVipConfigurationForm;
import hqsc.ray.wcc.jpa.repository.OpenVipConfigurationRepository;
import hqsc.ray.wcc.jpa.service.OpenVipConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-04-07
 */
@Service
@AllArgsConstructor
public class OpenVipConfigurationServiceImpl implements OpenVipConfigurationService {
	
	private final OpenVipConfigurationRepository openVipConfigurationRepository;
	
	
	/**
	 * 获取开通会员的配置列表
	 *
	 * @param openVipConfigurationForm
	 * @return
	 */
	@Override
	public PageMap<OpenVipConfigurationDto> listOpenMembershipConfigurations(OpenVipConfigurationForm openVipConfigurationForm) {
		Specification<OpenVipConfiguration> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
//				if (!StringUtils.empty(articleForm.getSectionName())) {
//					Join<Object, Object> section = root.join("section");
//					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
//				}
			
			if (openVipConfigurationForm.getStatus() != null) {
				pr.add(criteriaBuilder.equal(root.get("status"), openVipConfigurationForm.getStatus()));
			}
			if (openVipConfigurationForm.getIsDelete() != null) {
				pr.add(criteriaBuilder.equal(root.get("isDelete"), openVipConfigurationForm.getIsDelete()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sort")));
			return criteriaQuery.getRestriction();
		};
		List<OpenVipConfiguration> openVipConfigurationList;
		Long count;
		if (openVipConfigurationForm.getPageNow() == -1) {
			openVipConfigurationList = openVipConfigurationRepository.findAll(specification);
			count = Long.valueOf(openVipConfigurationList.size());
		} else {
			Pageable pageable = PageRequest.of(openVipConfigurationForm.getPageNow() - 1, openVipConfigurationForm.getPageSize());
			Page<OpenVipConfiguration> openMembershipConfigurationPage = openVipConfigurationRepository.findAll(specification, pageable);
			openVipConfigurationList = openMembershipConfigurationPage.getContent();
			count = openMembershipConfigurationPage.getTotalElements();
		}
		List<OpenVipConfigurationDto> list = new ArrayList<>();
		OpenVipConfigurationDto openVipConfigurationDto;
		for (OpenVipConfiguration openVipConfiguration : openVipConfigurationList) {
			openVipConfigurationDto = new OpenVipConfigurationDto();
			BeanUtils.copyProperties(openVipConfiguration, openVipConfigurationDto);
			openVipConfigurationDto.setPaymentModeText(openVipConfiguration.getPaymentMode() == null ? "" : openVipConfiguration.getPaymentMode().getText());
			list.add(openVipConfigurationDto);
		}
		return PageMap.of(count, list);
	}
	
	/**
	 * 保存更新配置
	 *
	 * @param openVipConfigurationForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(OpenVipConfigurationForm openVipConfigurationForm) {
		OpenVipConfiguration openVipConfiguration = null;
		
		
		Optional<OpenVipConfiguration> openMembershipConfigurationOptional = openVipConfigurationRepository.findById(openVipConfigurationForm.getId() == null ? 0L : openVipConfigurationForm.getId());
		if (openMembershipConfigurationOptional.isPresent()) {
			openVipConfiguration = openMembershipConfigurationOptional.get();
			
			openVipConfiguration.setSettingName(openVipConfigurationForm.getSettingName())
					.setPaymentMode(openVipConfigurationForm.getPaymentMode())
					.setOriginalPrice(openVipConfigurationForm.getOriginalPrice())
					.setPresentPrice(openVipConfigurationForm.getPresentPrice())
					.setNextPrice(openVipConfigurationForm.getNextPrice())
					.setSort(openVipConfigurationForm.getSort())
					.setDayNum(openVipConfigurationForm.getDayNum())
					.setValidityType(openVipConfigurationForm.getValidityType())
			;
		} else {
			openVipConfiguration = new OpenVipConfiguration();
			BeanUtils.copyProperties(openVipConfigurationForm, openVipConfiguration);
			openVipConfiguration.setStatus(1);
			openVipConfiguration.setIsDelete(0);
		}
		openVipConfigurationRepository.save(openVipConfiguration);
		return Result.success("保存成功");
	}
}
