package hqsc.ray.core.gray.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import hqsc.ray.core.common.constant.RayConstant;
import hqsc.ray.core.gray.support.RibbonFilterContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class GrayFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 设置请求头header信息
        Enumeration<String> headerNames = request.getHeaderNames();
        if (null != headerNames) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                // 若version版本号为空，则赋值默认版本号
                if (name.equals(RayConstant.VERSION) && StringUtils.isBlank(value)) {
                    value = RayConstant.DEFAULT_VERSION;
                }
                template.header(name, value);
            }
        }

        // 设置灰度版本
        String version = request.getHeader(RayConstant.VERSION);
        RibbonFilterContextHolder.getCurrentContext().add(RayConstant.VERSION, version);
    }
}
