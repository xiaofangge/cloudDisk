package com.fang.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT工具类
 * @author 川川
 * @date 2022-02-15 16:57
 */
public class JWTUtil {
    public static final String JWT_ID = UUID.randomUUID().toString();

    public static final String JWT_SECRET = "c2hpeWFubG91MjAyMTAzMDc=";

    /**
     * TODO 过期时间：一个星期
     */
    public static final int EXPIRE_TIME = 60 * 60 * 1000* 24 * 7;


    /**
     * 生成密钥
     * @author xinchao
     * @date 2022/2/15 17:04
     * @return SecretKey
     */
    private static SecretKey generateSecretKey() {
        String secret = JWT_SECRET;
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /** 
     * 解析jwt
     * @author xinchao
     * @date 2022/2/15 21:26
     * @param jwt jwt串
     * @return Claims 
     */
    public static Claims parseJWT(String jwt) {
        // 签名密钥，和生成的签名的密钥一模一样
        SecretKey secretKey = generateSecretKey();

        return Jwts.parser()
                .setSigningKey(secretKey)   // 设置签名的密钥
                .parseClaimsJws(jwt)        // 设置需要解析的jwt
                .getBody();
    }

    /**
     * 创建jwt
     * @author xinchao
     * @date 2022/2/15 21:13
     * @param issuer    jwt签发者
     * @param audience  jwt签收者
     * @param subject   用户信息，可以将多个关键信息转为JSON进行存放
     * @return String   JWT字符串
     */
    public static String createJWT(String issuer, String audience, String subject) {
        // 设置签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Map<String, Object> claims = new HashMap<>(2);
        claims.put("username", "admin");
        claims.put("password", "123456");

        long nowTime = System.currentTimeMillis();
        Date issueAt = new Date(nowTime);
        SecretKey secretKey = generateSecretKey();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setId(JWT_ID)
                .setIssuedAt(issueAt)
                .setIssuer(issuer)
                .setSubject(subject)
                .signWith(signatureAlgorithm, secretKey);

        // 设置过期时间
        long expTime = EXPIRE_TIME;
        if (expTime >= 0) {
            long exp = nowTime + expTime;
            jwtBuilder.setExpiration(new Date(exp));
        }

        jwtBuilder.setAudience(audience);

        // 返回：一个紧凑的 URL 安全 JWT 字符串
        return jwtBuilder.compact();
    }
}

