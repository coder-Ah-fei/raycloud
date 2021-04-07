package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.OpenMembershipConfigurationDto;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.entity.OpenMembershipConfiguration;
import hqsc.ray.wcc.jpa.form.OpenMembershipConfigurationForm;
import hqsc.ray.wcc.jpa.repository.OpenMembershipConfigurationRepository;
import hqsc.ray.wcc.jpa.service.OpenMembershipConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class OpenMembershipConfigurationServiceImpl implements OpenMembershipConfigurationService {
	
	private final OpenMembershipConfigurationRepository openMembershipConfigurationRepository;
	
	
	/**
	 * 获取开通会员的配置列表
	 *
	 * @param openMembershipConfigurationForm
	 * @return
	 */
	@Override
	public PageMap<OpenMembershipConfigurationDto> listOpenMembershipConfigurations(OpenMembershipConfigurationForm openMembershipConfigurationForm) {
		Specification<OpenMembershipConfiguration> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<OpenMembershipConfiguration> openMembershipConfigurationList;
		Long count;
		if (openMembershipConfigurationForm.getPageNow() == -1) {
			openMembershipConfigurationList = openMembershipConfigurationRepository.findAll(specification);
			count = Long.valueOf(openMembershipConfigurationList.size());
		} else {
			Pageable pageable = PageRequest.of(openMembershipConfigurationForm.getPageNow() - 1, openMembershipConfigurationForm.getPageSize());
			Page<OpenMembershipConfiguration> openMembershipConfigurationPage = openMembershipConfigurationRepository.findAll(specification, pageable);
			openMembershipConfigurationList = openMembershipConfigurationPage.getContent();
			count = openMembershipConfigurationPage.getTotalElements();
		}
		List<OpenMembershipConfigurationDto> list = new ArrayList<>();
		OpenMembershipConfigurationDto openMembershipConfigurationDto;
		for (OpenMembershipConfiguration openMembershipConfiguration : openMembershipConfigurationList) {
			openMembershipConfigurationDto = new OpenMembershipConfigurationDto();
			BeanUtils.copyProperties(openMembershipConfiguration, openMembershipConfigurationDto);
			openMembershipConfigurationDto.setPaymentModeText(openMembershipConfiguration.getPaymentMode() == null ? "" : openMembershipConfiguration.getPaymentMode().getText());
			list.add(openMembershipConfigurationDto);
		}
		return PageMap.of(count, list);
	}
	
	/**
	 * 保存更新配置
	 *
	 * @param openMembershipConfigurationForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(OpenMembershipConfigurationForm openMembershipConfigurationForm) {
		OpenMembershipConfiguration openMembershipConfiguration = null;
		Optional<OpenMembershipConfiguration> openMembershipConfigurationOptional = openMembershipConfigurationRepository.findById(openMembershipConfigurationForm.getId() == null ? 0L : openMembershipConfigurationForm.getId());
		if (openMembershipConfigurationOptional.isPresent()) {
			openMembershipConfiguration = openMembershipConfigurationOptional.get();
			BeanUtils.copyProperties(openMembershipConfigurationForm, openMembershipConfiguration);
		} else {
			openMembershipConfiguration = new OpenMembershipConfiguration();
			BeanUtils.copyProperties(openMembershipConfigurationForm, openMembershipConfiguration);
			openMembershipConfiguration.setStatus(1);
			openMembershipConfiguration.setIsDelete(0);
		}
		openMembershipConfigurationRepository.save(openMembershipConfiguration);
		return Result.success("保存成功");
	}
}
