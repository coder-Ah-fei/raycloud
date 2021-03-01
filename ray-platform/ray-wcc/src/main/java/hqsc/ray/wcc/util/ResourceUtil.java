package hqsc.ray.wcc.util;

import java.util.ResourceBundle;

/**
 * Created by admin on 2017/11/9.
 */
public class ResourceUtil {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("sysConfig");
    public static String getRandCodeType() {
        return bundle.getString("randCodeType");
    }
    public static String getRandCodeLength() {
        return bundle.getString("randCodeLength");
    }
    public static String getConfigByName(String key){
        return bundle.getString(key);
    }


}
