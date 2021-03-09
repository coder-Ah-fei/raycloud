package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccGoodsInfoDto;
import hqsc.ray.wcc.jpa.dto.WccMerchantBrandDetailDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccGoodsInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccMerchantBrandDetail;
import hqsc.ray.wcc.jpa.form.WccMerchantBrandDetailForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccMerchantBrandDetailRepository;
import hqsc.ray.wcc.jpa.service.WccMerchantBrandDetailService;
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
 * 描述：
 *
 * @author Administrator
 */
@Service
@AllArgsConstructor
public class WccMerchantBrandDetailServiceImpl implements WccMerchantBrandDetailService {
	
	private final WccMerchantBrandDetailRepository wccMerchantBrandDetailRepository;
	private final RaySysAttachmentRepository raySysAttachmentRepository;
	
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
			wccMerchantBrandDetailDto.setIconId(jpaWccMerchantBrandDetail.getIcon() == null ? 0L : jpaWccMerchantBrandDetail.getIcon().getId());
			if (wccMerchantBrandDetailForm.isGetGoodsInfo()) {
				List<JpaWccGoodsInfo> goodsInfoList = jpaWccMerchantBrandDetail.getGoodsInfoList();
				List<WccGoodsInfoDto> goodsInfoDtos = new ArrayList<>();
				WccGoodsInfoDto wccGoodsInfoDto;
				for (JpaWccGoodsInfo jpaWccGoodsInfo : goodsInfoList) {
					wccGoodsInfoDto = new WccGoodsInfoDto();
					BeanUtils.copyProperties(jpaWccGoodsInfo, wccGoodsInfoDto);
					wccGoodsInfoDto.setImgId(jpaWccGoodsInfo.getImg() == null ? 0 : jpaWccGoodsInfo.getImg().getId());
					goodsInfoDtos.add(wccGoodsInfoDto);
				}
				wccMerchantBrandDetailDto.setGoodsInfoList(goodsInfoDtos);
			}
			list.add(wccMerchantBrandDetailDto);
		}
		return new ResultMap<>(ResultMap.SUCCESS_CODE, PageMap.of(count, list));
	}
	
	/**
	 * 品牌方/商家详情表信息
	 *
	 * @param wccMerchantBrandDetailForm
	 * @return
	 */
	@Override
	public WccMerchantBrandDetailDto findById(WccMerchantBrandDetailForm wccMerchantBrandDetailForm) {
		Optional<JpaWccMerchantBrandDetail> brandDetailOptional = wccMerchantBrandDetailRepository.findById(wccMerchantBrandDetailForm.getId());
		if (!brandDetailOptional.isPresent()) {
			throw new RuntimeException("品牌方/商家不存在");
		}
		JpaWccMerchantBrandDetail jpaWccMerchantBrandDetail = brandDetailOptional.get();
		WccMerchantBrandDetailDto wccMerchantBrandDetailDto = new WccMerchantBrandDetailDto();
		BeanUtils.copyProperties(jpaWccMerchantBrandDetail, wccMerchantBrandDetailDto);
		wccMerchantBrandDetailDto.setIconId(jpaWccMerchantBrandDetail.getIcon() == null ? 0L : jpaWccMerchantBrandDetail.getIcon().getId());
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
		return wccMerchantBrandDetailDto;
	}
	
	/**
	 * 品牌方/商家详情表设置,支持新增或修改
	 *
	 * @param wccMerchantBrandDetailForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(WccMerchantBrandDetailForm wccMerchantBrandDetailForm) {
		JpaWccMerchantBrandDetail jpaWccMerchantBrandDetail = null;
		Optional<JpaWccMerchantBrandDetail> brandDetailOptional = wccMerchantBrandDetailRepository.findById(wccMerchantBrandDetailForm.getId() == null ? 0L : wccMerchantBrandDetailForm.getId());
		if (brandDetailOptional.isPresent()) {
			jpaWccMerchantBrandDetail = brandDetailOptional.get();
			BeanUtils.copyProperties(wccMerchantBrandDetailForm, jpaWccMerchantBrandDetail);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccMerchantBrandDetailForm.getIconId() == null ? 0L : wccMerchantBrandDetailForm.getIconId());
			if (attachmentOptional.isPresent()) {
				jpaWccMerchantBrandDetail.setIcon(attachmentOptional.get());
			}
		} else {
			jpaWccMerchantBrandDetail = new JpaWccMerchantBrandDetail();
			
			BeanUtils.copyProperties(wccMerchantBrandDetailForm, jpaWccMerchantBrandDetail);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccMerchantBrandDetailForm.getIconId() == null ? 0L : wccMerchantBrandDetailForm.getIconId());
			if (attachmentOptional.isPresent()) {
				jpaWccMerchantBrandDetail.setIcon(attachmentOptional.get());
			}
			jpaWccMerchantBrandDetail.setStatus(1);
			jpaWccMerchantBrandDetail.setIsDelete(0);
		}
		wccMerchantBrandDetailRepository.save(jpaWccMerchantBrandDetail);
		return Result.success("保存成功");
	}
	
}
