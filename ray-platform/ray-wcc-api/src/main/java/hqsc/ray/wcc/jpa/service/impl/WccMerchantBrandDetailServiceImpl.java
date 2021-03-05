package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccGoodsInfoDto;
import hqsc.ray.wcc.jpa.dto.WccMerchantBrandDetailDto;
import hqsc.ray.wcc.jpa.entity.JpaWccGoodsInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccMerchantBrandDetail;
import hqsc.ray.wcc.jpa.form.WccMerchantBrandDetailForm;
import hqsc.ray.wcc.jpa.repository.WccMerchantBrandDetailRepository;
import hqsc.ray.wcc.jpa.service.WccMerchantBrandDetailService;
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
public class WccMerchantBrandDetailServiceImpl implements WccMerchantBrandDetailService {
	
	@Autowired
	private WccMerchantBrandDetailRepository wccMerchantBrandDetailRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccMerchantBrandDetailForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccMerchantBrandDetails(WccMerchantBrandDetailForm wccMerchantBrandDetailForm) {
		Specification<JpaWccMerchantBrandDetail> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccMerchantBrandDetail> jpaWccMerchantBrandDetailList;
		Long count;
		if (wccMerchantBrandDetailForm.getPageNow() == -1) {
			jpaWccMerchantBrandDetailList = wccMerchantBrandDetailRepository.findAll(specification);
			count = Long.valueOf(jpaWccMerchantBrandDetailList.size());
		} else {
			Pageable pageable = PageRequest.of(wccMerchantBrandDetailForm.getPageNow() - 1, wccMerchantBrandDetailForm.getPageSize());
			Page<JpaWccMerchantBrandDetail> wccMerchantBrandDetailPage = wccMerchantBrandDetailRepository.findAll(specification, pageable);
			jpaWccMerchantBrandDetailList = wccMerchantBrandDetailPage.getContent();
			count = wccMerchantBrandDetailPage.getTotalElements();
		}
		List<WccMerchantBrandDetailDto> list = new ArrayList<>();
		WccMerchantBrandDetailDto wccMerchantBrandDetailDto;
		for (JpaWccMerchantBrandDetail jpaWccMerchantBrandDetail : jpaWccMerchantBrandDetailList) {
			wccMerchantBrandDetailDto = new WccMerchantBrandDetailDto();
			BeanUtils.copyProperties(jpaWccMerchantBrandDetail, wccMerchantBrandDetailDto);
			if (wccMerchantBrandDetailForm.isGetGoodsInfo()) {
				List<JpaWccGoodsInfo> goodsInfoList = jpaWccMerchantBrandDetail.getGoodsInfoList();
				List<WccGoodsInfoDto> goodsInfoDtos = new ArrayList<>();
				WccGoodsInfoDto wccGoodsInfoDto;
				for (JpaWccGoodsInfo jpaWccGoodsInfo : goodsInfoList) {
					wccGoodsInfoDto = new WccGoodsInfoDto();
					BeanUtils.copyProperties(jpaWccGoodsInfo, wccGoodsInfoDto);
					goodsInfoDtos.add(wccGoodsInfoDto);
				}
				wccMerchantBrandDetailDto.setGoodsInfoList(goodsInfoDtos);
			}
			list.add(wccMerchantBrandDetailDto);
		}
		return new ResultMap<>(ResultMap.SUCCESS_CODE, PageMap.of(count, list));
	}
	
}
