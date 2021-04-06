package hqsc.ray.core.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.common.exception.BaseException;
import hqsc.ray.core.common.exception.PreviewException;
import hqsc.ray.core.common.exception.TokenException;

import java.io.FileNotFoundException;

/**
 * Springboot WEB应用全局异常处理
 *
 * @author pangu
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class BaseExceptionHandler {
	
	/**
	 * BaseException 异常捕获处理
	 *
	 * @param ex 自定义BaseException异常类型
	 * @return Result
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Result<?> handleException(BaseException ex) {
		log.error("程序异常：" + ex.getMessage());
		return Result.fail(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
	}
	
	/**
	 * TokenException 异常捕获处理
	 *
	 * @param ex 自定义TokenException异常类型
	 * @return Result
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Result<?> handleException(TokenException ex) {
		log.error("程序异常==>errorCode:{}, exception:{}", HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
		return Result.fail(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
	}
	
	/**
	 * FileNotFoundException,NoHandlerFoundException 异常捕获处理
	 *
	 * @param exception 自定义FileNotFoundException异常类型
	 * @return Result
	 */
	@ExceptionHandler({FileNotFoundException.class, NoHandlerFoundException.class})
	public Result<?> noFoundException(Exception exception) {
		log.error("程序异常==>errorCode:{}, exception:{}", HttpStatus.NOT_FOUND.value(), exception.getMessage());
		return Result.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
	
	/**
	 * PreviewException 空指针异常捕获处理
	 *
	 * @param ex 自定义PreviewException异常类型
	 * @return Result
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result<?> handleException(PreviewException ex) {
		log.error("程序异常：" + ex.getMessage());
		return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	}
	
	/**
	 * NullPointerException 空指针异常捕获处理
	 *
	 * @param ex 自定义NullPointerException异常类型
	 * @return Result
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result<?> handleException(NullPointerException ex) {
		log.error("程序异常：{}" + ex.getMessage());
		return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	}
	
	/**
	 * NullPointerException 空指针异常捕获处理
	 *
	 * @param ex 自定义NullPointerException异常类型
	 * @return Result
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Result<?> handleException(AccessDeniedException ex) {
		log.error("程序异常：{}" + ex.getMessage());
		return Result.fail(HttpStatus.FORBIDDEN.value(), ex.getMessage());
	}
	
	
	/**
	 * 通用Exception异常捕获
	 *
	 * @param ex 自定义Exception异常类型
	 * @return Result
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result<?> handleException(Exception ex) {
		log.error("程序异常：" + ex.getMessage());
		String message = ex.getMessage();
		if (StringUtils.contains(message, "Bad credentials")) {
			message = "您输入的密码不正确";
		} else if (StringUtils.contains(ex.toString(), "InternalAuthenticationServiceException")) {
			message = "您输入的用户名不存在";
		} else if (StringUtils.contains(ex.toString(), "WechatUserInfoTypeException")) {
			message = "{\n" +
					"  \"errorCode\": \"getUserInfoTypeError\",\n" +
					"  \"errorMsg\": \"当前用户不存在，请使用getUserProfile接口获取用户的详细信息\"\n" +
					"}";
		}
		
		return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
	}
}
