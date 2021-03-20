package hqsc.ray.wcc.jpa.dto;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * ResultMap
 *
 * @author yangy
 * @date 2020-02-13
 */
public class ResultMap<T> {
	
	/**
	 * 成功代码
	 */
	public static final int SUCCESS_CODE = 0;
	/**
	 * 失败代码
	 */
	public static final int ERROR_CODE = 1;
	
	/**
	 * 未登录代码
	 */
	public static final int UN_LOGIN_CODE = 1002;
	/**
	 * 错误
	 */
	public static final String ERROR_MSG = "ERROR";
	/**
	 * 警告
	 */
	public static final String WARN_MSG = "WARN";
	/**
	 * 成功
	 */
	public static final String SUCCESS_MSG = "SUCCESS";
	
	/**
	 * 返回代码，默认错误
	 */
	private int code = ERROR_CODE;
	
	/**
	 * 返回信息， 默认错误
	 */
	private String message;
	
	/**
	 * 返回的数据
	 */
	private T result;
	
	public ResultMap() {
	}
	
	private ResultMap(int code) {
		this.code = code;
	}
	
	private ResultMap(String message) {
		this.message = message;
	}
	
	private ResultMap(int code, T result) {
		this.code = code;
		this.result = result;
	}
	
	private ResultMap(int code, String message, T result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}
	
	@NotNull
	@Contract(value = " -> new", pure = true)
	public static ResultMap of() {
		return new ResultMap();
	}
	
	
	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static ResultMap of(int code) {
		return new ResultMap(code);
	}
	
	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static ResultMap of(String message) {
		return new ResultMap(message);
	}
	
	@NotNull
	@Contract(value = "_, _ -> new", pure = true)
	public static ResultMap of(int code, Object result) {
		return new ResultMap(code, result);
	}
	
	@NotNull
	@Contract(value = " -> new", pure = true)
	public static ResultMap success() {
		return new ResultMap(SUCCESS_CODE);
	}
	
	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static ResultMap success(String message) {
		return new ResultMap(SUCCESS_CODE, message, null);
	}
	
	public static ResultMap success(String message, Object result) {
		return new ResultMap(SUCCESS_CODE, message, result);
	}
	
	public int getCode() {
		return code;
	}
	
	public ResultMap setCode(int code) {
		this.code = code;
		return this;
	}
	
	public String getMessage() {
		return message;
	}
	
	public ResultMap setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public T getResult() {
		return result;
	}
	
	public ResultMap setResult(T result) {
		this.result = result;
		return this;
	}
}
