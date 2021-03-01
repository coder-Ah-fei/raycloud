package hqsc.ray.core.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import hqsc.ray.core.common.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * 基础控制类
 * @author aristotle
 * @since 2020-06-01
 */
public class BaseController {

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @Autowired
    protected HttpServletRequest req;

    @Autowired
    protected HttpServletResponse res;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtil.parseLocalDateTime(text, DateUtil.DATETIME_FORMATTER));
            }
        });
    }


}
