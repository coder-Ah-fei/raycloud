package hqsc.ray.wcc2.service.impl;

import hqsc.ray.wcc2.dto.ResultMap;
import hqsc.ray.wcc2.dto.WccUserMessageDto;
import hqsc.ray.wcc2.entity.WccUserMessage;
import hqsc.ray.wcc2.form.WccUserMessageForm;
import hqsc.ray.wcc2.repository.WccUserMessageRepository;
import hqsc.ray.wcc2.service.WccUserMessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
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
public class WccUserMessageServiceImpl implements WccUserMessageService {
	
	@Autowired
	private WccUserMessageRepository wccUserMessageRepository;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserMessageForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUserMessages(WccUserMessageForm wccUserMessageForm) {
		Specification<WccUserMessage> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
			if (wccUserMessageForm.getUserId() != null) {
				Join<Object, Object> wccUser = root.join("wccUser");
				pr.add(criteriaBuilder.equal(wccUser.get("id"), wccUserMessageForm.getUserId()));
			}
			if (wccUserMessageForm.getMessageType() != null) {
				pr.add(criteriaBuilder.equal(root.get("messageType").as(Integer.class), wccUserMessageForm.getMessageType()));
			}
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<WccUserMessage> wccUserMessageList;
		if (wccUserMessageForm.getPageNow() == -1) {
			wccUserMessageList = wccUserMessageRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserMessageForm.getPageNow() - 1, wccUserMessageForm.getPageSize());
			Page<WccUserMessage> wccUserMessagePage = wccUserMessageRepository.findAll(specification, pageable);
			wccUserMessageList = wccUserMessagePage.getContent();
		}
		List<WccUserMessageDto> list = new ArrayList<>();
		WccUserMessageDto wccUserMessageDto;
		for (WccUserMessage wccUserMessage : wccUserMessageList) {
			wccUserMessageDto = new WccUserMessageDto();
			BeanUtils.copyProperties(wccUserMessage, wccUserMessageDto);
			list.add(wccUserMessageDto);
		}
		long count = wccUserMessageRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return new ResultMap<>(ResultMap.SUCCESS_CODE, map);
	}
	
}
