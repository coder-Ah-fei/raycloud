package hqsc.ray.core.auth.social;

import me.zhyd.oauth.config.AuthSource;

/**
 * @program: raycloud
 * @description:
 * @author: Ryan Hsu
 * @create: 2021-01-09 01:03
 **/
public enum AuthOtherSource implements AuthSource {
    WECHAT_MINI {
        public String authorize() {
            return "https://api.weixin.qq.com/sns/jscode2session";
        }

        public String accessToken() {
            return "https://api.weixin.qq.com/sns/jscode2session";
        }

        public String userInfo() {
            return "https://api.weixin.qq.com/sns/userinfo";
        }

        public String refresh() {
            return "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        }
    };
    AuthOtherSource() {
    }
}
