package hqsc.ray.wcc2.dto;

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
	
	public ResultMap(int code) {
		this.code = code;
	}
	
	public ResultMap(String message) {
		this.message = message;
	}
	
	public ResultMap(int code, T result) {
		this.code = code;
		this.result = result;
	}
	
	public static ResultMap of() {
		return new ResultMap();
	}
	
	
	public static ResultMap of(int code) {
		return new ResultMap(code);
	}
	
	public static ResultMap of(String message) {
		return new ResultMap(message);
	}
	
	public static ResultMap of(int code, Object result) {
		return new ResultMap(code, result);
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
