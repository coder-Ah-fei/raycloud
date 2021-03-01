package hqsc.ray.core.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户详细扩展
 * @author pangu
 */
public interface RayUserDetailsService extends UserDetailsService {

    /**
     * 根据手机号登录
     * @param mobile
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;

    /**
     * 根据社交账号登录
     * @param openId 第三方的绑定的openId
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserBySocial(String openId) throws UsernameNotFoundException;

    /**
     * 乘客根据社交账号登录
     * @param openId 第三方的绑定的openId
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadWUserBySocial(String openId) throws UsernameNotFoundException;

    /**
     * 创建用户
     * @param unionId 第三方的绑定的unionId
     * @param nickname 用户昵称
     * @param avatar 头像URL
     * @param sex 性别
     */
    Boolean createWccUser(String unionId, String nickname, Long avatarId, String avatar, String sex);


    Long createAtt(String avatar);
}
