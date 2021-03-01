package hqsc.ray.core.auth.aspect;

import hqsc.ray.core.auth.annotation.UserAuth;
import hqsc.ray.core.common.constant.Oauth2Constant;
import hqsc.ray.core.common.entity.LoginUser;
import hqsc.ray.core.common.exception.TokenException;
import hqsc.ray.core.common.util.SecurityUtil;
import hqsc.ray.core.common.util.StringPool;
import hqsc.ray.core.redis.core.RedisService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 自定义权限验证
 *
 * @author pangu
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class UserAuthAspect {

	/**
	 * 所有权限标识
	 */
	private static final String ALL_PERMISSION = "*:*:*";

	private final HttpServletRequest request;

	private final RedisService redisService;

	@Around("@annotation(hqsc.ray.core.auth.annotation.UserAuth)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Signature signature = point.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		UserAuth userAuth = method.getAnnotation(UserAuth.class);
		if (ObjectUtils.isEmpty(userAuth)) {
			return point.proceed();
		}

		if (hasPerm(userAuth.hasPerm())) {
			return point.proceed();
		} else {
			throw new TokenException("权限验证不通过");
		}
	}


	/**
	 * 验证用户是否具备某权限
	 *
	 * @param permission 权限字符串
	 * @return 用户是否具备某权限
	 */
	public boolean hasPerm(String permission) {
		LoginUser userInfo = SecurityUtil.getUsername(request);

		if (StringUtils.isEmpty(userInfo)) {
			return false;
		}
		if(!Oauth2Constant.USER_TYPE_USER.equals(userInfo.getUserType())){
			return false;
		}

		if (!StringUtils.isEmpty(userInfo) && StringUtils.isEmpty(permission)) {
			return true;
		}
		Map<String, Object> data = (Map<String, Object>) redisService.get(Oauth2Constant.RAY_PERMISSION_PREFIX
				+ userInfo.getAccount() + StringPool.DOT + userInfo.getRoleId());
		List<String> authorities = (List<String>) data.get("permissions");
		return hasPermissions(authorities, permission);
	}

	/**
	 * 判断是否包含权限
	 *
	 * @param authorities 权限列表
	 * @param permission  权限字符串
	 * @return 用户是否具备某权限
	 */
	private boolean hasPermissions(Collection<String> authorities, String permission) {
		return authorities.stream().filter(StringUtils::hasText)
				.anyMatch(x -> ALL_PERMISSION.contains(x) || PatternMatchUtils.simpleMatch(permission, x));
	}
}
