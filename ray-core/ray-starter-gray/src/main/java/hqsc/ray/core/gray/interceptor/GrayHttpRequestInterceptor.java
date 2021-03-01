package hqsc.ray.core.gray.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import hqsc.ray.core.common.constant.RayConstant;
import hqsc.ray.core.gray.support.RibbonFilterContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * RestTemplate拦截器
 * @author pangu
 * @since 2020-7-20
 */
public class GrayHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = attributes.getRequest();

        // 设置请求头header信息
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        if (null != headerNames) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = servletRequest.getHeader(name);
                // 若version版本号为空，则赋值默认版本号
                if (name.equals(RayConstant.VERSION) && StringUtils.isBlank(value)) {
                    value = RayConstant.DEFAULT_VERSION;
                }
                request.getHeaders().add(name, value);
            }
        }

        // 设置灰度版本
        String version = servletRequest.getHeader(RayConstant.VERSION);
        RibbonFilterContextHolder.getCurrentContext().add(RayConstant.VERSION, version);

        return execution.execute(request, body);
    }

}
