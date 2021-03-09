package hqsc.ray.wcc.jpa.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.wcc.jpa.dto.PageMap;
import hqsc.ray.wcc.jpa.dto.SysAreaDto;
import hqsc.ray.wcc.jpa.dto.TengxunDili;
import hqsc.ray.wcc.jpa.dto.WccBannerDto;
import hqsc.ray.wcc.jpa.entity.JpaSysArea;
import hqsc.ray.wcc.jpa.form.SysAreaForm;
import hqsc.ray.wcc.jpa.repository.SysAreaRepository;
import hqsc.ray.wcc.jpa.service.SysAreaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class SysAreaServiceImpl implements SysAreaService {
	
	@Autowired
	private SysAreaRepository sysAreaRepository;
	
	/**
	 * 获取数据
	 *
	 * @param sysAreaForm
	 * @return ResultMap
	 */
	@Override
	public PageMap<WccBannerDto> listSysAreas(SysAreaForm sysAreaForm) {
		Specification<JpaSysArea> specification = (root, criteriaQuery, criteriaBuilder) -> {
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
		List<JpaSysArea> jpaSysAreaList;
		Long count = 0L;
		if (sysAreaForm.getPageNow() == -1) {
			jpaSysAreaList = sysAreaRepository.findAll(specification);
			count = Long.valueOf(jpaSysAreaList.size());
		} else {
			Pageable pageable = PageRequest.of(sysAreaForm.getPageNow() - 1, sysAreaForm.getPageSize());
			Page<JpaSysArea> sysAreaListPage = sysAreaRepository.findAll(specification, pageable);
			jpaSysAreaList = sysAreaListPage.getContent();
			count = sysAreaListPage.getTotalElements();
		}
		List<SysAreaDto> list = new ArrayList<>();
		SysAreaDto sysAreaDto;
		for (JpaSysArea jpaSysArea : jpaSysAreaList) {
			sysAreaDto = new SysAreaDto();
			BeanUtils.copyProperties(jpaSysArea, sysAreaDto);
			
			
			list.add(sysAreaDto);
		}
		return PageMap.of(count, list);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save() {
		String result1 = HttpUtil.get("https://apis.map.qq.com/ws/district/v1/list?key=7VHBZ-36ZKX-JFE4U-TOFGJ-S5MYT-GMBJS");
		
		TengxunDili tengxunDili = JSON.parseObject(result1, TengxunDili.class);
		List<List<TengxunDili.ResultDTO>> result = tengxunDili.getResult();
		List<TengxunDili.ResultDTO> resultDTOS0 = result.get(0);
		List<TengxunDili.ResultDTO> resultDTOS1 = result.get(1);
		List<TengxunDili.ResultDTO> resultDTOS2 = result.get(2);
		for (TengxunDili.ResultDTO resultDTO : resultDTOS0) {
			JpaSysArea jpaSysArea0 = new JpaSysArea();
			jpaSysArea0.setAdcode(String.valueOf(resultDTO.getId()));
			jpaSysArea0.setLevel(1);
			jpaSysArea0.setName(resultDTO.getName());
			jpaSysArea0.setFullname(resultDTO.getFullname());
			jpaSysArea0.setProvince(resultDTO.getFullname());
			jpaSysArea0.setLocation(resultDTO.getLocation().getLat() + "," + resultDTO.getLocation().getLng());
			List<String> pinyin = resultDTO.getPinyin();
			String str = "";
			for (String s : pinyin) {
				str += s + ",";
			}
			if (str.endsWith(",")) {
				str = str.substring(0, str.length() - 1);
			}
			jpaSysArea0.setPinyin(str);
			jpaSysArea0 = sysAreaRepository.save(jpaSysArea0);
			List<TengxunDili.ResultDTO> resultDTO1S = resultDTOS1.subList(resultDTO.getCidx().get(0), resultDTO.getCidx().get(1) + 1);
			JpaSysArea jpaSysArea1 = null;
			for (TengxunDili.ResultDTO resultDTO1 : resultDTO1S) {
				if (resultDTO1.getCidx() == null) {
					List<JpaSysArea> byAdcode = sysAreaRepository.findByAdcode(String.valueOf(resultDTO1.getId()).substring(0, 4) + "00");
					if (byAdcode.size() == 0) {
						jpaSysArea1 = new JpaSysArea();
						jpaSysArea1.setAdcode(String.valueOf(resultDTO1.getId()).substring(0, 4) + "00");
						jpaSysArea1.setLevel(2);
						jpaSysArea1.setName(jpaSysArea0.getName());
						jpaSysArea1.setFullname(jpaSysArea0.getFullname());
						jpaSysArea1.setProvince(jpaSysArea0.getFullname());
						jpaSysArea1.setCity(jpaSysArea0.getFullname());
						jpaSysArea1.setLocation(resultDTO1.getLocation().getLat() + "," + resultDTO1.getLocation().getLng());
						jpaSysArea1.setParent(jpaSysArea0);
						List<String> pinyin1 = resultDTO1.getPinyin();
						String str1 = "";
						for (String s : pinyin1) {
							str1 += s + ",";
						}
						if (str1.endsWith(",")) {
							str1 = str1.substring(0, str1.length() - 1);
						}
						jpaSysArea1.setPinyin(str1);
						jpaSysArea1 = sysAreaRepository.save(jpaSysArea1);
					}
					
					
					JpaSysArea jpaSysArea2 = new JpaSysArea();
					jpaSysArea2.setAdcode(String.valueOf(resultDTO1.getId()));
					jpaSysArea2.setLevel(3);
					jpaSysArea2.setName(resultDTO1.getName());
					jpaSysArea2.setFullname(resultDTO1.getFullname());
					jpaSysArea2.setProvince(jpaSysArea0.getFullname());
					jpaSysArea2.setCity(jpaSysArea1.getFullname());
					jpaSysArea2.setCounty(jpaSysArea1.getFullname());
					jpaSysArea2.setLocation(resultDTO1.getLocation().getLat() + "," + resultDTO1.getLocation().getLng());
					jpaSysArea2.setParent(jpaSysArea1);
					List<String> pinyin2 = resultDTO1.getPinyin();
					String str2 = "";
					for (String s : pinyin2) {
						str2 += s + ",";
					}
					if (str2.endsWith(",")) {
						str2 = str2.substring(0, str2.length() - 1);
					}
					jpaSysArea2.setPinyin(str2);
					jpaSysArea2 = sysAreaRepository.save(jpaSysArea2);
					continue;
				}
				
				
				jpaSysArea1 = new JpaSysArea();
				jpaSysArea1.setAdcode(String.valueOf(resultDTO1.getId()));
				jpaSysArea1.setLevel(2);
				jpaSysArea1.setName(resultDTO1.getName());
				jpaSysArea1.setFullname(resultDTO1.getFullname());
				jpaSysArea1.setProvince(jpaSysArea0.getFullname());
				jpaSysArea1.setCity(resultDTO1.getFullname());
				jpaSysArea1.setLocation(resultDTO1.getLocation().getLat() + "," + resultDTO1.getLocation().getLng());
				jpaSysArea1.setParent(jpaSysArea0);
				List<String> pinyin1 = resultDTO1.getPinyin();
				String str1 = "";
				for (String s : pinyin1) {
					str1 += s + ",";
				}
				if (str1.endsWith(",")) {
					str1 = str1.substring(0, str1.length() - 1);
				}
				jpaSysArea1.setPinyin(str1);
				jpaSysArea1 = sysAreaRepository.save(jpaSysArea1);
				
				List<TengxunDili.ResultDTO> resultDTO2S = resultDTOS2.subList(resultDTO1.getCidx().get(0), resultDTO1.getCidx().get(1) + 1);
				
				for (TengxunDili.ResultDTO resultDTO2 : resultDTO2S) {
					JpaSysArea jpaSysArea2 = new JpaSysArea();
					jpaSysArea2.setAdcode(String.valueOf(resultDTO2.getId()));
					jpaSysArea2.setLevel(3);
					jpaSysArea2.setName(resultDTO2.getName());
					jpaSysArea2.setFullname(resultDTO2.getFullname());
					jpaSysArea2.setProvince(jpaSysArea0.getFullname());
					jpaSysArea2.setCity(jpaSysArea1.getFullname());
					jpaSysArea2.setCounty(resultDTO2.getFullname());
					jpaSysArea2.setLocation(resultDTO2.getLocation().getLat() + "," + resultDTO2.getLocation().getLng());
					jpaSysArea2.setParent(jpaSysArea1);
					List<String> pinyin2 = resultDTO2.getPinyin();
					if (pinyin2 != null) {
						String str2 = "";
						for (String s : pinyin2) {
							str2 += s + ",";
						}
						if (str2.endsWith(",")) {
							str2 = str2.substring(0, str2.length() - 1);
						}
						jpaSysArea2.setPinyin(str2);
					}
					jpaSysArea2 = sysAreaRepository.save(jpaSysArea2);
				}
			}
			
		}
		
		
		return null;
	}
	
	@Override
	public Result<?> list() {
		Map<String, Object> result = new HashMap<>();
		List<JpaSysArea> provinceList = sysAreaRepository.findByLevelOrderById(1);
		Map<String, Map<String, Object>> provinceMap = new HashMap<>();
		Map<String, Object> province = new HashMap<>();
		for (JpaSysArea jpaSysArea : provinceList) {
			province.put(jpaSysArea.getAdcode(), jpaSysArea.getFullname());
		}
		result.put("province_list", province);
		
		List<JpaSysArea> cityList = sysAreaRepository.findByLevelOrderById(2);
		Map<String, Map<String, Object>> cityMap = new HashMap<>();
		Map<String, Object> city = new HashMap<>();
		for (JpaSysArea jpaSysArea : cityList) {
			
			city.put(jpaSysArea.getAdcode(), jpaSysArea.getFullname());
		}
		result.put("city_list", city);
		
		
		List<JpaSysArea> countyList = sysAreaRepository.findByLevelOrderById(3);
		Map<String, Map<String, Object>> countyMap = new HashMap<>();
		Map<String, Object> county = new HashMap<>();
		for (JpaSysArea jpaSysArea : countyList) {
			county.put(jpaSysArea.getAdcode(), jpaSysArea.getFullname());
		}
		result.put("county_list", county);
		
		return Result.data(result);
	}
	
}
