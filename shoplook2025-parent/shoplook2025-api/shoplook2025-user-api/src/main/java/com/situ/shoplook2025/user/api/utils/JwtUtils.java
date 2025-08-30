package com.situ.shoplook2025.user.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    public static String createJwt(String id, String username, Map<String, Object> payloads,
                                   LocalDateTime expireTime, String secret) {
        try {
            // 转换LocalDateTime为Date
            Date expireDate = Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant());

            JWTCreator.Builder builder = JWT.create();
            String jwt = builder.withPayload(payloads)
                    .withExpiresAt(expireDate)
                    .withIssuer("shoplook2025")
                    .withIssuedAt(new Date())
                    .withAudience(username)
                    .withSubject("身份认证")
                    .withJWTId(id)
                    .sign(Algorithm.HMAC256(secret));
            return jwt;
        } catch (Exception e) {
            throw new RuntimeException("JWT生成失败", e);
        }
    }
}
