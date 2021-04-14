package hqsc.ray.wcc.jpa.service.impl;

import com.alibaba.fastjson.JSONObject;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.util.AesHelper;
import hqsc.ray.core.common.util.DateUtil;
import hqsc.ray.core.common.util.StringUtil;
import hqsc.ray.core.redis.core.RedisService;
import hqsc.ray.wcc.jpa.dto.MemberInfoDto;
import hqsc.ray.wcc.jpa.dto.ResultMap;
import hqsc.ray.wcc.jpa.dto.WccUserDto;
import hqsc.ray.wcc.jpa.entity.JpaSysAttachment;
import hqsc.ray.wcc.jpa.entity.JpaWccUser;
import hqsc.ray.wcc.jpa.entity.MemberInfo;
import hqsc.ray.wcc.jpa.form.WccUserForm;
import hqsc.ray.wcc.jpa.repository.MemberInfoRepository;
import hqsc.ray.wcc.jpa.repository.RaySysAttachmentRepository;
import hqsc.ray.wcc.jpa.repository.WccUserRepository;
import hqsc.ray.wcc.jpa.service.WccUserConcernService;
import hqsc.ray.wcc.jpa.service.WccUserService;
import hqsc.ray.wcc.wechatModel.GetPhoneModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@RequiredArgsConstructor
public class WccUserServiceImpl implements WccUserService {
	
	private final WccUserRepository userRepository;
	private final WccUserConcernService userConcernService;
	private final RedisService redisService;
	private final MemberInfoRepository memberInfoRepository;
	private final RaySysAttachmentRepository attachmentRepository;
	
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
		Long count = 0L;
		if (wccUserForm.getPageNow() == -1) {
			jpaWccUserList = userRepository.findAll(specification);
			count = Long.valueOf(jpaWccUserList.size());
		} else {
			Pageable pageable = PageRequest.of(wccUserForm.getPageNow() - 1, wccUserForm.getPageSize());
			Page<JpaWccUser> wccUserPage = userRepository.findAll(specification, pageable);
			jpaWccUserList = wccUserPage.getContent();
			count = wccUserPage.getTotalElements();
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
			
			MemberInfo memberInfo = jpaWccUser.getMemberInfo();
			if (memberInfo != null) {
				MemberInfoDto memberInfoDto = new MemberInfoDto();
				memberInfoDto.setId(memberInfoDto.getId())
						.setUserId(jpaWccUser.getId())
						.setStartDataTime(memberInfo.getStartDataTime())
						.setEndDataTime(memberInfo.getEndDataTime())
						.setPaymentMode(memberInfo.getPaymentMode())
						.setPaymentModeText(memberInfo.getPaymentMode() == null ? "" : memberInfo.getPaymentMode().getText())
				;
				wccUserDto.setMemberInfoDto(memberInfoDto);
			}
			
			
			list.add(wccUserDto);
		}
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
		Optional<JpaWccUser> optionalJpaWccUser = userRepository.findById(Long.valueOf(userId));
		if (!optionalJpaWccUser.isPresent()) {
			return Result.fail("用户不存在");
		}
		JpaWccUser jpaWccUser = optionalJpaWccUser.get();
		jpaWccUser.setPhone(getPhoneModel.getPhoneNumber());
		userRepository.save(jpaWccUser);
		return Result.success("手机号绑定成功").setData(StringUtil.hideString(jpaWccUser.getPhone()));
	}
	
	/**
	 * 保存新增/修改用户
	 *
	 * @param userForm
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> save(WccUserForm userForm) {
		
		Optional<JpaWccUser> userOptional = userRepository.findById(userForm.getId() == null ? 0 : userForm.getId());
		JpaWccUser user = new JpaWccUser();
		if (userOptional.isPresent()) {
			user = userOptional.get();
			user.setStatus(1)
					.setIsDelete(0)
			;
		}
		BeanUtils.copyProperties(userForm, user);
		Optional<JpaSysAttachment> attachmentOptional = attachmentRepository.findById(userForm.getJpaSysAttachmentId());
		if (attachmentOptional.isPresent()) {
			user.setJpaSysAttachment(attachmentOptional.get());
		}
		
		
		userRepository.save(user);
		
		if (userForm.getMember() == 1) {
			MemberInfo memberInfo = user.getMemberInfo();
			if (memberInfo == null) {
				memberInfo = new MemberInfo();
				memberInfo.setWccUser(user);
			}
			memberInfo.setPaymentMode(userForm.getPaymentMode())
					.setStartDataTime(DateUtil.parseLocalDateTime(userForm.getMemberDateTimeStart(), DateUtil.DATETIME_FORMATTER))
					.setEndDataTime(DateUtil.parseLocalDateTime(userForm.getMemberDateTimeEnd(), DateUtil.DATETIME_FORMATTER))
					.setStatus(1)
					.setIsDelete(0);
			memberInfoRepository.save(memberInfo);
		}
		return Result.condition(true);
	}
	
}
