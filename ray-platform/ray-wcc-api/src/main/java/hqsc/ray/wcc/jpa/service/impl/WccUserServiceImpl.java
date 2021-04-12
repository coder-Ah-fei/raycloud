package hqsc.ray.wcc.jpa.service.impl;

import com.alibaba.fastjson.JSONObject;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.util.AesHelper;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.redis.core.RedisService;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccUserDto;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.form.WccUserForm;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.WccUserConcernService;
import hqsc.ray.wcc.jpa.service.WccUserService;
import hqsc.ray.wcc.wechatModel.GetPhoneModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * 描述：
 *
 * @author Administrator
 */
@Service
public class WccUserServiceImpl implements WccUserService {
	
	@Autowired
	private WccUserRepository wccUserRepository;
	@Autowired
	private WccUserConcernService userConcernService;
	@Autowired
	private RedisService redisService;
	
	/**
	 * 获取数据
	 *
	 * @param wccUserForm
	 * @return ResultMap
	 */
	@Override
	public ResultMap listWccUsers(WccUserForm wccUserForm) {
		Specification<JpaWccUser> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> pr = new ArrayList<>();
//				if (!StringUtils.empty(articleForm.getSectionName())) {
//					Join<Object, Object> section = root.join("section");
//					pr.add(builder.equal(section.get("sectionName"), articleForm.getSectionName()));
//				}
			
			if (wccUserForm.getTalentFlag() != null) {
				pr.add(criteriaBuilder.equal(root.get("talentFlag"), wccUserForm.getTalentFlag()));
			}
			if (wccUserForm.getCelebrityFlag() != null) {
				pr.add(criteriaBuilder.equal(root.get("celebrityFlag"), wccUserForm.getCelebrityFlag()));
			}
			if (wccUserForm.getStarFlag() != null) {
				pr.add(criteriaBuilder.equal(root.get("starFlag"), wccUserForm.getStarFlag()));
			}
			
			criteriaQuery.where(pr.toArray(new Predicate[pr.size()]));
			
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("creationDate")));
			return criteriaQuery.getRestriction();
		};
		List<JpaWccUser> jpaWccUserList;
		if (wccUserForm.getPageNow() == -1) {
			jpaWccUserList = wccUserRepository.findAll(specification);
		} else {
			Pageable pageable = PageRequest.of(wccUserForm.getPageNow() - 1, wccUserForm.getPageSize());
			Page<JpaWccUser> wccUserPage = wccUserRepository.findAll(specification, pageable);
			jpaWccUserList = wccUserPage.getContent();
		}
		List<WccUserDto> list = new ArrayList<>();
		WccUserDto wccUserDto;
		for (JpaWccUser jpaWccUser : jpaWccUserList) {
			wccUserDto = new WccUserDto();
			BeanUtils.copyProperties(jpaWccUser, wccUserDto);
			wccUserDto.setNickname(StringUtil.toUnicode(jpaWccUser.getNickname()));
			wccUserDto.setJpaSysAttachmentId(jpaWccUser.getJpaSysAttachment() == null ? 0 : jpaWccUser.getJpaSysAttachment().getId());
			wccUserDto.setConcernCount(0L);
			if (wccUserForm.getId() != null) {
				Long aLong = userConcernService.countByJpaWccUserIdAndBelongUserId(wccUserForm.getId(), jpaWccUser.getId());
				wccUserDto.setConcernCount(aLong);
			}
			list.add(wccUserDto);
		}
		long count = wccUserRepository.count(specification);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		return ResultMap.success("'", map);
	}
	
	/**
	 * 绑定手机号
	 *
	 * @param encryptedData
	 * @param iv
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> bindMobilePhone(String encryptedData, String iv, String userId) {
		String sessionKey = (String) redisService.get("session_key");
		String phoneJsonStr = null;
		try {
			phoneJsonStr = AesHelper.decryptForWeChatApplet(encryptedData, sessionKey, iv);
		} catch (Exception exception) {
			exception.printStackTrace();
			return Result.fail("绑定手机号失败");
		}
		GetPhoneModel getPhoneModel = JSONObject.parseObject(phoneJsonStr, GetPhoneModel.class);
		Optional<JpaWccUser> optionalJpaWccUser = wccUserRepository.findById(Long.valueOf(userId));
		if (!optionalJpaWccUser.isPresent()) {
			return Result.fail("用户不存在");
		}
		JpaWccUser jpaWccUser = optionalJpaWccUser.get();
		jpaWccUser.setPhone(getPhoneModel.getPhoneNumber());
		wccUserRepository.save(jpaWccUser);
		return Result.success("手机号绑定成功").setData(StringUtil.hideString(jpaWccUser.getPhone()));
	}
	
}
