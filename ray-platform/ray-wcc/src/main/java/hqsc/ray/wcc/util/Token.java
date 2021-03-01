package hqsc.ray.wcc.util;


import hqsc.ray.wcc.entity.WccUser;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Token {

    private final static String HQSC_TOKEN_KEY = "TIC2019";

    private final static Integer HQSC_TOKEN_TIME = 60 * 60 * 24 * 3;//秒

    private final static Integer HQSC_TOKEN_TIME_HM = HQSC_TOKEN_TIME * 1000; //毫秒


    //生成token
    public static String getToken(WccUser model) {
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        Map<String, Object> payload = new HashMap<String, Object>();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        long currentTime = System.currentTimeMillis();
        currentTime = currentTime + HQSC_TOKEN_TIME_HM;
        RedisUtil.setObject(uuid, model, HQSC_TOKEN_TIME);
        RedisUtil.setObject(uuid + "bo", true, HQSC_TOKEN_TIME);
        RedisUtil.setObject(uuid + "currentTime", currentTime, HQSC_TOKEN_TIME);
        payload.put("uuid", uuid);
//        header.put("exp", currentTime);
//        String token = Base64.getBase64(JSONObject.fromObject(header).toString()) + "." + Base64.getBase64(JSONObject.fromObject(payload).toString());
        String token = Base64.getBase64(JSONObject.fromObject(payload).toString());
        token = token + "." + HMACSHA256.encode(token, HQSC_TOKEN_KEY);
        return token;
    }

    //验证token,status: -1:无效,0:超期,1:成功
    public static Map<String, Object> verfiy(String token) {
        Map<String, Object> result = new HashMap<String, Object>();
        String[] datas = token.split("\\.");
        if (datas.length == 2) {
//            String header = "";
            String payload = "";
            String hs = "";
            try {
//                header = URLDecoder.decode(datas[0], "UTF-8");
                payload = URLDecoder.decode(datas[0], "UTF-8");
                hs = URLDecoder.decode(datas[1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String str = HMACSHA256.encode(payload, HQSC_TOKEN_KEY);
            if (str.equals(hs)) {
                JSONObject json = JSONObject.fromObject(Base64.getFromBase64(payload));
//                JSONObject json2 = JSONObject.fromObject(Base64.getFromBase64(header));
//                long currentTime = System.currentTimeMillis();
//                if (currentTime < Long.valueOf(json2.get("exp").toString())) {
                String uuid = json.getString("uuid");
                result.put("uuid", uuid);
                WccUser model = (WccUser) RedisUtil.getObject(uuid);
                if (RedisUtil.getObject(uuid + "bo") == null) {
                    result.put("status", -1);
                    return result;
                }
                Boolean bo = (Boolean) RedisUtil.getObject(uuid + "bo");
                if (bo) {
                    if (model == null) {
                        result.put("status", -1);
                    } else {
                        result.put("userId", model.getUserId());
                        result.put("status", 1);
                        Long currentTimeUuid = (Long) RedisUtil.getObject(uuid + "currentTime");
                        long currentTime = System.currentTimeMillis();
                        if (currentTime > currentTimeUuid) {

                            currentTime = currentTime + HQSC_TOKEN_TIME_HM;
                            RedisUtil.setObject(uuid + "currentTime", currentTime, HQSC_TOKEN_TIME);
                        } else {
                            RedisUtil.setObject(uuid + "currentTime", currentTimeUuid, HQSC_TOKEN_TIME);
                        }
                        RedisUtil.setObject(uuid, model, HQSC_TOKEN_TIME);
                        RedisUtil.setObject(uuid + "bo", true, HQSC_TOKEN_TIME);
                    }
                } else {
                    result.put("status", -1);
                }
//                } else {
//                    result.put("status", 0);
//                }
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", -1);
        }
        return result;
    }

    /**
     * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
     *
     * @param request
     * @return ip
     */
    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }
}
