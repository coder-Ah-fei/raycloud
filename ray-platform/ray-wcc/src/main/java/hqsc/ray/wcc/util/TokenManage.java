package hqsc.ray.wcc.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenManage {
    //密钥
    private static final String TOKEN_SECRET = "5R5roUcuAu3o5C3o";
    //30分钟超时
    private static final long TIME_OUT = 30 * 60 * 1000;
    //加密
    public static String sign(Long uid) {
        try {
            Date expiration_time = new Date(System.currentTimeMillis() + TIME_OUT);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> headerMap = new HashMap<>(2);
            headerMap.put("type", "JWT");
            headerMap.put("alg", "HS256");
            return JWT.create().withHeader(headerMap).withClaim("uid", uid).withExpiresAt(expiration_time).sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }
    //解密
    public static DecodedJWT verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (Exception e) {
            //解码异常
            return null;
        }
    }

    public static void main(String[] args) {
        String token = sign(170L);
        System.out.println("token::" + token);
        DecodedJWT jwt = verify(token);
        if (jwt != null) {
            //UID
            System.out.println("uid::" + jwt.getClaim("uid").asLong());
            //TIMEOUT
            System.out.println("timeout::" + jwt.getExpiresAt());
            //ALG
            System.out.println("alg::" + jwt.getAlgorithm());
            //TOKEN
            System.out.println("token::" + jwt.getToken());
            //HEADER
            System.out.println("header::" + jwt.getHeader());
            //PAYLOAD
            System.out.println("payload::" + jwt.getPayload());
            //SIGNATURE
            System.out.println("signature::" + jwt.getSignature());
        } else {
            System.out.println("Decoded JWT Failure");
        }
    }
}