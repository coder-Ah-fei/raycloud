package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.PageMap;
import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccMcnInfoDto;
import hqsc.ray.wcc2.entity.WccMcnInfo;
import hqsc.ray.wcc2.form.WccMcnInfoForm;
import hqsc.ray.wcc2.repository.WccMcnInfoRepository;
import hqsc.ray.wcc2.service.WccMcnInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccMcnInfoServiceImpl implements WccMcnInfoService {
	
	@Autowired
	private WccMcnInfoRepository wccMcnInfoRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccMcnInfoForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccMcnInfos(WccMcnInfoForm wccMcnInfoForm) {
		Specification<WccMcnInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<WccMcnInfo> wccMcnInfoList;
		Long count;
		if (wccMcnInfoForm.getPageNow() == -1) {
			wccMcnInfoList = wccMcnInfoRepository.findAll(specification);
			count = Long.valueOf(wccMcnInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccMcnInfoForm.getPageNow() - 1, wccMcnInfoForm.getPageSize());
			Page<WccMcnInfo> wccMcnInfoPage = wccMcnInfoRepository.findAll(specification, pageable);
			wccMcnInfoList = wccMcnInfoPage.getContent();
			count = wccMcnInfoPage.getTotalElements();
		}
		List<WccMcnInfoDto> list = new ArrayList<>();
		WccMcnInfoDto wccMcnInfoDto;
		for (WccMcnInfo wccMcnInfo : wccMcnInfoList) {
			wccMcnInfoDto = new WccMcnInfoDto();
			BeanUtils.copyProperties(wccMcnInfo, wccMcnInfoDto);
			wccMcnInfoDto.setIconId(wccMcnInfo.getIcon() == null ? null : wccMcnInfo.getIcon().getId());
			
			list.add(wccMcnInfoDto);
		}
		return new ResultMap<>(ResultMap.SUCCESS_CODE, PageMap.of(count, list));
	}
	
}
