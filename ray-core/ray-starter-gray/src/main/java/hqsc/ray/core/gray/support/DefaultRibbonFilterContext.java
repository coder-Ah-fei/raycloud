package hqsc.ray.core.gray.support;

import org.apache.commons.lang3.StringUtils;
import hqsc.ray.core.common.constant.RayConstant;
import hqsc.ray.core.gray.api.RibbonFilterContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认灰度负载过滤上下文实现类
 * @author pangu
 * @since 2020-7-26
 */
public class DefaultRibbonFilterContext implements RibbonFilterContext {

    private final Map<String, String> attributes = new HashMap<>();

    @Override
    public RibbonFilterContext add(String key, String value) {
        // 若version版本号为空，则赋值默认版本号
        if (key.equals(RayConstant.VERSION) && StringUtils.isBlank(value)) {
            value = RayConstant.DEFAULT_VERSION;
        }
        attributes.put(key, value);
        return this;
    }

    @Override
    public String get(String key) {
        return attributes.get(key);
    }

    @Override
    public RibbonFilterContext remove(String key) {
        attributes.remove(key);
        return this;
    }

    @Override
    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }
}
