package hqsc.ray.wcc.util;


import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Args;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 工具类
 * Created by hqsc on 2016/12/07.
 */
public class StringUtil {

    private static final Pattern linePattern = Pattern.compile("_(\\w)");
    private static final Pattern humpPattern = Pattern.compile("[A-Z]");


    /**
     * 首页非登陆记录数
     */
    public static final String userid="1";

    public static final long  indexpageCurrent = 1 ;

    public static final long indexpageSize=20;
    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */

    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }

    /**
     * 驼峰转下划线,效率比上面高
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine(String)})
     *
     * @param str
     * @return
     */
    public static String humpToLine2(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuffer()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * object转String
     *
     * @param object
     * @return
     */
    public static String getString(Object object) {
        return getString(object, "");
    }

    public static String getString(Object object, String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return object.toString();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Integer
     *
     * @param object
     * @return
     */
    public static int getInt(Object object) {
        return getInt(object, -1);
    }

    public static int getInt(Object object, Integer defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int toInt(String s) {
        if (s != null && !"".equals(s.trim())) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 截取字符串
     *
     * @param s   源字符串
     * @param jmp 跳过jmp
     * @param sb  取在sb
     * @param se  于se
     * @return 之间的字符串
     */
    public static String subStringExe(String s, String jmp, String sb, String se) {
        if (isEmpty(s)) {
            return "";
        }
        int i = s.indexOf(jmp);
        if (i >= 0 && i < s.length()) {
            s = s.substring(i + 1);
        }
        i = s.indexOf(sb);
        if (i >= 0 && i < s.length()) {
            s = s.substring(i + 1);
        }
        if (se == "") {
            return s;
        } else {
            i = s.indexOf(se);
            if (i >= 0 && i < s.length()) {
                s = s.substring(i + 1);
            }
            return s;
        }
    }

    /**
     * 判断是否是空字符串 null和"" 都返回 true
     *
     * @param s
     * @return
     * @author Robin Chang
     */
    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    /**
     * 判断是否是空字符串 null和"" null返回result,否则返回字符串
     *
     * @param s
     * @return
     */
    public static String isEmpty(String s, String result) {
        if (s != null && !s.equals("")) {
            return s;
        }
        return result;
    }

    /**
     * object转Boolean
     *
     * @param object
     * @return
     */
    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }

    public static boolean getBoolean(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * @Description: 字符串数组连接
     * @Param: [delimiter：分隔符, elements：字符串数组]
     * @return: 连接后的字符串
     * @Author: Ryan Hsu
     * @Date: 2018-03-05
     */
    public static String join(String delimiter, String[] elements) {
        String result = "";
        for (int i = 0; i < elements.length; i++) {
            if (i == 0) {
                result = elements[i];
            } else {
                result += delimiter + elements[i];
            }
        }
        return result;
    }

    /**
     * @Description: 本地化日期转换字符串
     * @Param: [date：日期, pattern：模式]
     * @return: 日期字符串
     * @Author: Ryan Hsu
     * @Date: 2018-06-01
     */
    public static String formatDate(Date date, String pattern, String zone) {
        Args.notNull(date, "Date");
        Args.notNull(pattern, "Pattern");
        if(StringUtils.isBlank(zone)){
            zone="GMT+08";
        }
        SimpleDateFormat formatter = StringUtil.formatFor(pattern,zone);
        return formatter.format(date);
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal();

    public static SimpleDateFormat formatFor(String pattern, String zone) {
        SoftReference<Map<String, SimpleDateFormat>> ref = THREADLOCAL_FORMATS.get();
        Map<String, SimpleDateFormat> formats = ref == null ? null : ref.get();
        if (formats == null) {
            formats = new HashMap();
            THREADLOCAL_FORMATS.set(new SoftReference(formats));
        }

        SimpleDateFormat format = (SimpleDateFormat) ((Map) formats).get(pattern);
        if (format == null) {
            format = new SimpleDateFormat(pattern, Locale.US);
            format.setTimeZone(TimeZone.getTimeZone(zone));
            ((Map) formats).put(pattern, format);
        }

        return format;
    }

    /**
     * @Description: 格式化文件大小
     * @Param: [size, len]
     * @return: java.lang.String
     * @Author: Ryan Hsu
     * @Date: 2018-09-17
     */
    public static String formatSize(long size, int len) {
        Double d = new BigDecimal(size).doubleValue();
        Double s = d / 1024;
        if (s < 1024) {
            return new BigDecimal(s).setScale(len, BigDecimal.ROUND_HALF_UP).toString() + "K";
        }
        s = d / 1024 / 1024;
        if (s < 1024) {
            return new BigDecimal(s).setScale(len, BigDecimal.ROUND_HALF_UP).toString() + "M";
        }
        s = d / 1024 / 1024 / 1024;
        if (s < 1024) {
            return new BigDecimal(s).setScale(len, BigDecimal.ROUND_HALF_UP).toString() + "G";
        }

        s = d / 1024 / 1024 / 1024 / 1024;
        if (s < 1024) {
            return new BigDecimal(s).setScale(len, BigDecimal.ROUND_HALF_UP).toString() + "T";
        }
        s = d / 1024 / 1024 / 1024 / 1024 / 1024;
        return new BigDecimal(s).setScale(len, BigDecimal.ROUND_HALF_UP).toString() + "P";
    }


    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isTrimedEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isLineBroken(String s) {
        return s.contains("\n") || s.contains("\r");
    }

}
