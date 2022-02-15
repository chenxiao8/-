package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 李昕
 * @date 2021/12/21 11:07
 */
public class JwtTokenUtils {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearar ";

    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "esiek";

    private static final long EXPIRATION = 3600L;
    private static final long REMBERME = 1000 * 24 * 60 * 60 * 1000L;
    // 角色的key
    private static final String ROLE_CLAIMS = "rol";

    public static String createToken(String username,String role,boolean rememberMe){
        long expiration = rememberMe  ? REMBERME : EXPIRATION;
        HashMap<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET)  // 签名方式
                .setClaims(map)
                .setIssuer(ISS) // 签发者
                .setSubject(username)   // 主体
                .setIssuedAt(new Date())    // 签名时间
                .setExpiration(new Date(Instant.now().toEpochMilli() +expiration))// 过期时间
                .compact();
    }

    public  static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration()
                .before(new Date());
    }
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
    public static String getUserRole(String token){
        return (String)getTokenBody(token).get(ROLE_CLAIMS);
    }

}
