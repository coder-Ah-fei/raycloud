package hqsc.ray.wcc.controller.login;


import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hqsc.ray.core.common.api.Result;
import hqsc.ray.core.web.controller.BaseController;
import hqsc.ray.wcc.conf.WechatConf;
import hqsc.ray.wcc.entity.WccUser;
import hqsc.ray.wcc.service.IWccUserService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping({"/wechat"})
public class LoginController extends BaseController {

    @Autowired
    WechatConf wechatConf;

    private String openid;
    private String session_key;


    @Autowired
    private IWccUserService wccUserService;


    @PostMapping({"/getOpenId"})
    public Result<?> getOpenId(@RequestParam(required = true, defaultValue = "", value = "code") String code) {
        System.out.println("code==========="+code);
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        if(code != null && code.length() > 0 ){
            String url="https://api.weixin.qq.com/sns/jscode2session?appid="+wechatConf.getAppid()+"&secret="+wechatConf.getAppsecret()+"&js_code="+code+"&grant_type=authorization_code";
            try {
                // 创建uri
                URIBuilder builder = new URIBuilder(url);
                URI uri = builder.build();
                // 创建http GET请求
                HttpGet httpGet = new HttpGet(uri);
                // 执行请求
                response = httpclient.execute(httpGet);
                // 判断返回状态是否为200
                if (response.getStatusLine().getStatusCode() == 200) {
                    resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return Result.fail("code不存在！");
        }
        // 解析json
        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
        session_key = jsonObject.get("session_key")+"";
        openid = jsonObject.get("openid")+"";
        System.out.println("session_key=="+session_key);
        System.out.println("openid=="+openid);
        if (openid!=null&&!"".equals(openid)&&!"null".equals(openid)&&session_key!=null&&!"".equals(session_key)&&!"null".equals(session_key)){
            return Result.data(resultString);
        }else {
            return Result.fail("无效code！");
        }
    }


    /*
    * 用户登录
    * */
//    @PostMapping(value = {"/login"})
//    private Result<?> login(@RequestParam(required = false, defaultValue = "", value = "unionid") String unionid){
//
//        Map<String, Object> map = new HashMap<>();
//        WccUser wccUser;
//        if(unionid != null){
//            wccUser = wccUserService.selectOne(unionid);
//        }else {
//            return Result.fail("无效参数！");
//        }
//        map.put("wccUser",wccUser);
//        return wccUser==null?Result.fail("登陆失败！"):Result.data(map,"登陆成功！");
//        if (wccUser != null) {
//            if (!headimgurl.equals(wccUser.getWechatHeadPortraitAddress()) && !StringUtil.isEmpty(headimgurl)){
//                UploadUtils util = new UploadUtils();
//                util.setSavePath(ResourceUtil.getConfigByName("imgUrl") + "upload/");
//                util.setSaveUrl("/" + util.getBasePath() + "/");
//                util.setBasePath(util.getBasePath());
//                String[] infos = util.saveFileFromUrl(headimgurl);
//                String url = infos[1];
//                WccAttachment att = new WccAttachment();
//                att.setStatus(1);
//                att.setIsDelete(0);
//                att.setFilePath(url);
//                att.setFileExtend("jpg");
//                att.setCreatedBy("admin");
//                att.setCreatedByUser("admin");
//                Boolean b = wccAttachmentService.save(att);
//
//                if (wccUser.getHeadPortrait() != null) {
//                    wccAttachmentService.removeById(wccUser.getHeadPortrait());
//                }
//                wccUser.setWechatHeadPortraitAddress(headimgurl);
//                wccUser.setHeadPortrait(att.getAttachmentId());
//            }
//            wccUser.setLastUpdateDate(LocalDateTime.now());
//            wccUser.setNickname(nickName);
//            wccUser.setGender(Integer.parseInt(gender));
//            wccUserService.updateById(wccUser);
//            map.put("user",wccUser);
//            String token = Token.getToken(wccUser);
//            System.out.println("token--------------"+token);
//            map.put("token", token);
//            return Result.data(map);
//            return Result.success("登陆成功");
//        }else {
//            return Result.fail("请先注册后，再登录！");
//        }
//    }




//    @PostMapping(value = {"/test"})
//    public Result<?> test(@RequestParam String id){
//        System.out.println("进入方法");
//        WccUser wccUser = new WccUser();
//        wccUser.setName("叶祖德");
//        wccUser.setPhone("11111111111111");
//        wccUser.setGender(0);
//        wccUser.setNickname("yzd");
//        QueryWrapper<WccUser> wrapper = new QueryWrapper<>();
//        wrapper.eq("WECHAT_UNION_ID",id);
//        return Result.data(wccUserService.getOne(wrapper));
//    }
}
