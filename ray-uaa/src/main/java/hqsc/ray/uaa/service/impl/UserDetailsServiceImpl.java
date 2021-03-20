package hqsc.ray.uaa.service.impl;

import hqsc.ray.component.entity.SysAttachment;
import hqsc.ray.component.feign.ISysAttachmentProvider;
import hqsc.ray.core.common.constant.Oauth2Constant;
import hqsc.ray.core.common.exception.TokenException;
import hqsc.ray.core.security.userdetails.RayUser;
import hqsc.ray.core.security.userdetails.RayUserDetailsService;
import hqsc.ray.system.dto.UserInfo;
import hqsc.ray.system.entity.SysUser;
import hqsc.ray.system.feign.ISysUserProvider;
import hqsc.ray.wcc.entity.WccUser;
import hqsc.ray.wcc.feign.IWccUserProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 用户详情实现类
 *
 * @author pangu
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements RayUserDetailsService {
	
	public static final String ENABLE = "0";
	public static final String DISABLE = "1";
	public static final String DISABLE_USER = "0";
	
	@Resource
	private ISysUserProvider sysUserProvider;
	@Resource
	private ISysAttachmentProvider sysAttachmentProvider;
	@Resource
	private IWccUserProvider wccUserProvider;
	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserInfo userInfo = sysUserProvider.getUserByUserName(userName).getData();
		if (userInfo == null) {
			throw new TokenException("该用户：" + userName + "不存在");
		}
		userInfo.setType(Oauth2Constant.LOGIN_USERNAME_TYPE);
		userInfo.setUserName(userName);
		return getUserDetails(userInfo);
		
	}
	
	@Override
	public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
		UserInfo userInfo = sysUserProvider.getUserByMobile(mobile).getData();
		if (userInfo == null) {
			throw new TokenException("该用户：" + mobile + "不存在");
		}
		userInfo.setType(Oauth2Constant.LOGIN_MOBILE_TYPE);
		userInfo.setUserName(mobile);
		return getUserDetails(userInfo);
	}
	
	@Override
	public UserDetails loadUserBySocial(String openId) throws UsernameNotFoundException {
		String userName = "admin";
		UserInfo userInfo = sysUserProvider.getUserByUserName(userName).getData();
		if (userInfo == null) {
			throw new TokenException("该用户：" + userName + "不存在");
		}
		userInfo.setType(Oauth2Constant.LOGIN_USERNAME_TYPE);
		userInfo.setUserName(userName);
		return getUserDetails(userInfo);
	}
	
	@Override
	public UserDetails loadWUserBySocial(String openId) throws UsernameNotFoundException {
		UserInfo admin = sysUserProvider.getUserByUserName("admin").getData();
		
		hqsc.ray.wcc.dto.UserInfo user = wccUserProvider.getWUserByUnionId(openId).getData();
		if (user == null) {
			return null;
//			throw new TokenException("该用户：" + openId + "不存在");
		}
		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(user, userInfo);
		userInfo.setType(Oauth2Constant.LOGIN_SOCIAL_TYPE);
		userInfo.setUserName(openId);
		return getUserDetails(userInfo);
	}
	
	@Override
	public Long createAtt(String avatar) {
		SysAttachment sysAttachment = sysAttachmentProvider.crateAtt(avatar);
		return sysAttachment.getId();
	}
	
	@Override
	public Boolean createWccUser(String unionId, String nickname, Long avatarId, String avatar, String sex) {
		//封装用户信息
		boolean bo = true;
		WccUser wccUser = new WccUser();
		wccUser.setWechatUnionId(unionId);

//		String html = StringUtil.toHtml(nickname);
		wccUser.setNickname(nickname);
		wccUser.setHeadPortrait(avatarId);
		wccUser.setWechatHeadPortraitAddress(avatar);
		wccUser.setGender(Integer.parseInt(sex));
		wccUser.setCreationDate(LocalDateTime.now());
		int code = wccUserProvider.createWUser(wccUser).getCode();
		if (code == 400) {
			bo = false;
		}
		return bo;
	}
	
	
	private UserDetails getUserDetails(UserInfo userInfo) {
		if (ObjectUtils.isEmpty(userInfo)) {
			log.info("该用户：{} 不存在！", userInfo.getUserName());
			throw new TokenException("该用户：" + userInfo.getUserName() + "不存在");
		}
		boolean isDisable = false;
		if (Oauth2Constant.USER_TYPE_MANAGER.equals(userInfo.getUserType())) {
			if (DISABLE.equals(userInfo.getSysUser().getStatus())) {
				isDisable = true;
			}
		} else if (Oauth2Constant.USER_TYPE_USER.equals(userInfo.getUserType())) {
			if (DISABLE_USER.equals(userInfo.getWccUser().getStatus().toString())) {
				isDisable = true;
			}
		}
		if (isDisable) {
			log.info("该用户：{} 已被停用!", userInfo.getUserName());
			throw new TokenException("对不起，您的账号：" + userInfo.getUserName() + " 已停用");
		}
		Collection<? extends GrantedAuthority> authorities
				= AuthorityUtils.createAuthorityList("*:*:*");
		log.info("authorities: {}", authorities);
		if (Oauth2Constant.USER_TYPE_MANAGER.equals(userInfo.getUserType())) {
			SysUser user = userInfo.getSysUser();
			log.info("用户名：{}", user.getAccount());
			return new RayUser(user.getId(), userInfo.getType(), userInfo.getUserType(), user.getDepartId(), user.getRoleId(), user.getTelephone(), user.getAvatar(),
					user.getTenantId(), userInfo.getUserName(), user.getPassword(), ENABLE.equals(user.getStatus()),
					true, true, true,
					authorities);
		} else if (Oauth2Constant.USER_TYPE_USER.equals(userInfo.getUserType())) {
			WccUser wccUser = userInfo.getWccUser();
			log.info("用户姓名：{}", wccUser.getName());
			return new RayUser(wccUser.getUserId(), userInfo.getType(), userInfo.getUserType(), 0l, 0l, wccUser.getPhone(), "",
					"100000", userInfo.getUserName(), "", !DISABLE_USER.equals(wccUser.getStatus()),
					true, true, true,
					authorities);
		}
		return null;
	}
	
	
}
