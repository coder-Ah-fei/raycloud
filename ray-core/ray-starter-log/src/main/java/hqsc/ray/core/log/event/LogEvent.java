package hqsc.ray.core.log.event;

import hqsc.ray.core.common.dto.CommonLog;
import org.springframework.context.ApplicationEvent;

/**
 * 日志事件
 * @author pangu 7333791@qq.com
 * @since 2020-7-15
 */
public class LogEvent extends ApplicationEvent {

    public LogEvent(CommonLog source) {
        super(source);
    }
}
