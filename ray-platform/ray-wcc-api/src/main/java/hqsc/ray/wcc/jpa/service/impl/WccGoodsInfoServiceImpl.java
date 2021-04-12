package hqsc.ray.wcc.jpa.service.impl;

import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.WccGoodsInfoDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccGoodsInfo;
import hqsc.ray.wcc.jpa.entity.JpaWccMerchantBrandDetail;
import hqsc.ray.wcc.jpa.form.WccGoodsInfoForm;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccGoodsInfoRepository;
import hqsc.ray.wcc.jpa.repository.WccMerchantBrandDetailRepository;
import hqsc.ray.wcc.jpa.service.WccGoodsInfoService;
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
public class WccGoodsInfoServiceImpl implements WccGoodsInfoService {
	
	private final WccGoodsInfoRepository wccGoodsInfoRepository;
	private final RaySysAttachmentRepository raySysAttachmentRepository;
	private final WccMerchantBrandDetailRepository wccMerchantBrandDetailRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccGoodsInfoForm
	 * @return ResultMap
	 */
	@Override
	public PageMap<WccGoodsInfoDto> listWccGoodsInfos(WccGoodsInfoForm wccGoodsInfoForm) {
		Specification<JpaWccGoodsInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaWccGoodsInfo> jpaWccGoodsInfoList;
		Long count;
		if (wccGoodsInfoForm.getPageNow() == -1) {
			jpaWccGoodsInfoList = wccGoodsInfoRepository.findAll(specification);
			count = Long.valueOf(jpaWccGoodsInfoList.size());
		} else {
			Pageable pageable = PageRequest.of(wccGoodsInfoForm.getPageNow() - 1, wccGoodsInfoForm.getPageSize());
			Page<JpaWccGoodsInfo> wccGoodsInfoPage = wccGoodsInfoRepository.findAll(specification, pageable);
			jpaWccGoodsInfoList = wccGoodsInfoPage.getContent();
			count = wccGoodsInfoPage.getTotalElements();
		}
		List<WccGoodsInfoDto> list = new ArrayList<>();
		WccGoodsInfoDto wccGoodsInfoDto;
		for (JpaWccGoodsInfo jpaWccGoodsInfo : jpaWccGoodsInfoList) {
			wccGoodsInfoDto = new WccGoodsInfoDto();
			BeanUtils.copyProperties(jpaWccGoodsInfo, wccGoodsInfoDto);
			if (jpaWccGoodsInfo.getMerchantBrand() != null) {
				wccGoodsInfoDto.setMerchantBrandId(jpaWccGoodsInfo.getMerchantBrand().getId());
				wccGoodsInfoDto.setMerchantBrandName(jpaWccGoodsInfo.getMerchantBrand().getName());
			}
			wccGoodsInfoDto.setImgId(jpaWccGoodsInfo.getImg() == null ? 0L : jpaWccGoodsInfo.getImg().getId());
			list.add(wccGoodsInfoDto);
		}
		return PageMap.of(count, list);
	}
	
	/**
	 * 商品信息表信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public WccGoodsInfoDto findById(Long id) {
		Optional<JpaWccGoodsInfo> goodsInfoOptional = wccGoodsInfoRepository.findById(id);
		if (!goodsInfoOptional.isPresent()) {
			throw new RuntimeException("商品不存在");
		}
		JpaWccGoodsInfo jpaWccGoodsInfo = goodsInfoOptional.get();
		WccGoodsInfoDto wccGoodsInfoDto = new WccGoodsInfoDto();
		BeanUtils.copyProperties(jpaWccGoodsInfo, wccGoodsInfoDto);
		if (jpaWccGoodsInfo.getMerchantBrand() != null) {
			wccGoodsInfoDto.setMerchantBrandId(jpaWccGoodsInfo.getMerchantBrand().getId());
			wccGoodsInfoDto.setMerchantBrandName(jpaWccGoodsInfo.getMerchantBrand().getName());
		}
		wccGoodsInfoDto.setImgId(jpaWccGoodsInfo.getImg() == null ? 0L : jpaWccGoodsInfo.getImg().getId());
		
		return wccGoodsInfoDto;
	}
	
	/**
	 * 商品信息表设置,支持新增或修改
	 *
	 * @param wccGoodsInfoForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(WccGoodsInfoForm wccGoodsInfoForm) {
		JpaWccGoodsInfo jpaWccGoodsInfo = null;
		Optional<JpaWccGoodsInfo> goodsInfoOptional = wccGoodsInfoRepository.findById(wccGoodsInfoForm.getId() == null ? 0L : wccGoodsInfoForm.getId());
		if (goodsInfoOptional.isPresent()) {
			jpaWccGoodsInfo = goodsInfoOptional.get();
			BeanUtils.copyProperties(wccGoodsInfoForm, jpaWccGoodsInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccGoodsInfoForm.getImgId() == null ? 0L : wccGoodsInfoForm.getImgId());
			if (attachmentOptional.isPresent()) {
				jpaWccGoodsInfo.setImg(attachmentOptional.get());
			}
			Optional<JpaWccMerchantBrandDetail> brandDetailOptional = wccMerchantBrandDetailRepository.findById(wccGoodsInfoForm.getMerchantBrandId() == null ? 0L : wccGoodsInfoForm.getMerchantBrandId());
			if (brandDetailOptional.isPresent()) {
				jpaWccGoodsInfo.setMerchantBrand(brandDetailOptional.get());
			}
			
			
		} else {
			jpaWccGoodsInfo = new JpaWccGoodsInfo();
			
			BeanUtils.copyProperties(wccGoodsInfoForm, jpaWccGoodsInfo);
			Optional<JpaSysAttachment> attachmentOptional = raySysAttachmentRepository.findById(wccGoodsInfoForm.getImgId() == null ? 0L : wccGoodsInfoForm.getImgId());
			if (attachmentOptional.isPresent()) {
				jpaWccGoodsInfo.setImg(attachmentOptional.get());
			}
			Optional<JpaWccMerchantBrandDetail> brandDetailOptional = wccMerchantBrandDetailRepository.findById(wccGoodsInfoForm.getMerchantBrandId() == null ? 0L : wccGoodsInfoForm.getMerchantBrandId());
			if (brandDetailOptional.isPresent()) {
				jpaWccGoodsInfo.setMerchantBrand(brandDetailOptional.get());
			}
			jpaWccGoodsInfo.setStatus(1);
			jpaWccGoodsInfo.setIsDelete(0);
		}
		wccGoodsInfoRepository.save(jpaWccGoodsInfo);
		return Result.success("保存成功");
	}
	
}
