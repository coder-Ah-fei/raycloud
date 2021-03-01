package hqsc.ray.core.common.constant;

/**
 * 用户常量
 *
 * @author pangu
 */
public interface ProviderConstant {
    /**
     * 远程调用公共前缀
     */
    String PROVIDER = "/provider";

    /**
     * 根据id查询用户信息
     */
    String PROVIDER_USER_ID = PROVIDER + "/user/id";

    /**
     * 根据username查询用户信息
     */
    String PROVIDER_USER_USERNAME = PROVIDER + "/user/username";

    /**
     * 根据手机号查询用户信息
     */
    String PROVIDER_USER_MOBILE = PROVIDER + "/user/mobile";

    /**
     * 根据微信id查询网红用户信息
     */
    String PROVIDER_USER_UNIONID = PROVIDER + "/user/union";

    /**
     * 根据获取的微信信息添加前端用户信息
     */
    String PROVIDER_USER_ADD = PROVIDER + "/user/add";

    /**
     * 根据roleId查询menuId列表
     */
    String PROVIDER_ROLE_PERMISSION = PROVIDER + "/role-permission/permission";

    /**
     * 查询字典值
     */
    String PROVIDER_DICT_VALUE = PROVIDER + "/dict/value";

    /**
     * 查询字典列表
     */
    String PROVIDER_DICT_LIST = PROVIDER + "/dict/list";

    /**
     * 日志配置
     */
    String PROVIDER_LOG_SET = PROVIDER + "/log/set";

    /**
     * 添加头像记录
     */
    String PROVIDER_ATT_ADD = PROVIDER + "/att/add";

    /*
    * 根据id获取附件url
    * */
    String PROVIDER_ATT_URL = PROVIDER + "/att/url";

    /*
     * 根据id集合获取附件urls
     * */
    String PROVIDER_ATT_URLS = PROVIDER + "/att/urls";
}
