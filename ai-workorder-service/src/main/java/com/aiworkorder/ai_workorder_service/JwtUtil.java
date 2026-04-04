package com.aiworkorder.ai_workorder_service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;



//spring boot 通用组件类
@Component
public class JwtUtil {
    private final String Key="liu-jie-order-key-123456";//密钥
    private final long time=1000*60*60*24;//过期时间24小时

    //安全密钥
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Key.getBytes(StandardCharsets.UTF_8));
    //生成token
    public String generateToken(String username){

        //过期时间
        Date date=new Date(System.currentTimeMillis()+time);

        return Jwts.builder()
                .setSubject(username)       // 设置用户名（主题）
                .setIssuedAt(new Date())    // 设置签发时间
                .setExpiration(date)  // 设置过期时间
                .signWith(secretKey)              // 使用密钥签名
                .compact();
    }

    //解析token
    public String getToken(String token){
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //检验token
    public boolean isToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return  true;
        }catch (Exception e){
            return  false;
        }
    }

}
