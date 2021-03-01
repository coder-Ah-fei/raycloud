package hqsc.ray.core.encrypt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import hqsc.ray.core.encrypt.handler.EncryptHandler;
import hqsc.ray.core.encrypt.handler.InitHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 加密过滤器
 *
 * @author gaoyang
 */
@Slf4j
public class EncryptFilter implements Filter {

	private final EncryptHandler encryptHandler;

	private static final AtomicBoolean isEncryptAnnotation = new AtomicBoolean(false);
	private final static Set<String> encryptCacheUri = new HashSet<>();

	public EncryptFilter(EncryptHandler encryptHandler) {
		this.encryptHandler = encryptHandler;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if (isEncryptAnnotation.get()) {
			if (checkUri(((HttpServletRequest) servletRequest).getRequestURI())) {
				this.chain(servletRequest, servletResponse, filterChain);
			} else {
				filterChain.doFilter(servletRequest, servletResponse);
			}
		} else {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			String contentType = request.getContentType();
			if (request.getRequestURI().startsWith("/actuator") || contentType == null || !contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				this.chain(servletRequest, servletResponse, filterChain);
			}
		}
	}

	private void chain(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		EncryptRequestWrapper request = new EncryptRequestWrapper((HttpServletRequest) servletRequest, encryptHandler);
		EncryptResponseWrapper response = new EncryptResponseWrapper((HttpServletResponse) servletResponse);
		filterChain.doFilter(request, response);
		byte[] responseData = response.getResponseData();
		if (responseData.length == 0) {
			return;
		}
		log.info("接收的报文：" + new String(responseData));
		byte[] encryptByte = encryptHandler.encode(URLEncoder.encode(new String(responseData), "UTF-8").getBytes());
		log.info("加密后的报文：" + new String(encryptByte));
		servletResponse.setContentLength(-1);
		servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ServletOutputStream outputStream = servletResponse.getOutputStream();
		log.info("outputStream: " + outputStream.toString());
		outputStream.write(encryptByte);
		outputStream.flush();
	}


	private boolean checkUri(String uri) {
		uri = uri.replaceAll("//+", "/");
		if (uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}
        return encryptCacheUri.contains(uri);
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		InitHandler.handler(filterConfig, encryptCacheUri, isEncryptAnnotation);
	}


	@Override
	public void destroy() {

	}

}
