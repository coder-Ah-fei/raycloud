//package hqsc.ray.wcc.conf;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.Assert;
//
//
//@Configuration
//@ConfigurationProperties(prefix = "wechat", ignoreInvalidFields = true)
//public class AppConf implements InitializingBean {
//
//    private static String appid = "wx46d009089857bf16";
//    // 静态属性
//    private static String appsecret = "42a590dfa10fc2e1ed64e2f15ae49126";
//
//    public static String getAppid() {
//        return appid;
//    }
//
//    public static String getAppSecret() {
//        return appsecret;
//    }
//
//    // 注入静态配置值
//    public void setServerId(String serverId) {
////        AppConf.serverId = serverId;
////        if (serverId != 0) {
////            AppConf.master = false;
////        }
//    }
//
//    // 配置项校验
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //Assert.state(serverId > -1, "serverId必须大等0");
//    }
//}
