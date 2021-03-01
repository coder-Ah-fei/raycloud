package hqsc.ray.core.encrypt.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import hqsc.ray.core.common.enums.MethodType;
import hqsc.ray.core.encrypt.annotation.SignEncrypt;
import hqsc.ray.core.encrypt.handler.SignEncryptHandler;
import hqsc.ray.core.encrypt.wrapper.CacheRequestWrapper;
import hqsc.ray.core.encrypt.wrapper.EncryptRequestWrapperFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 签名加密拦截器
 *
 * @author gaoyang
 */
@AllArgsConstructor
public class SignEncryptInterceptor implements MethodInterceptor {

	private final String signSecret;
	private final SignEncryptHandler signEncryptHandler;

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Object proceed = methodInvocation.proceed();
		CacheRequestWrapper request = (CacheRequestWrapper) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		if (!MethodType.POST.name().equalsIgnoreCase(request.getMethod()) ||
				!EncryptRequestWrapperFactory.contentIsJson(request.getContentType())) {
			return proceed;
		}
		SignEncrypt annotation = methodInvocation.getMethod().getAnnotation(SignEncrypt.class);
		long timeout = annotation.timeout();
		TimeUnit timeUnit = annotation.timeUnit();
		if (request.getBody().length < 1) {
			return proceed;
		}
		Map<Object, Object> jsonMap = new ObjectMapper().readValue(request.getBody(), Map.class);
		return signEncryptHandler.handle(proceed, timeout, timeUnit, signSecret, jsonMap);
	}
}
