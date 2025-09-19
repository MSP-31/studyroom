package com.synclife.studyroom.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtService {
    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    // AccessToken 생성
    public String generateToken(Map<String, String> claims, long expiration) {
        return Jwts.builder()
                .header().add("typ", "JWT").and() // typ 헤더 추가
                .claims(claims) // 공개 클레임
                .id(Long.toHexString(System.nanoTime())) // jti(JWT ID) 클레임
                .issuedAt(new Date()) // 발급 시간 설정
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 만료 시간 설정
                .signWith(secretKey) // 서명을 생성
                .compact(); // JWT 토큰을 생성
    }

    // 만료 여부 체크
    public boolean validateToken(String token) {
        return !getClaims(token).getExpiration().before(new Date());
    }

    // 서명 검증
    public Claims getClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(secretKey) // 토큰 서명 검증
                    .build()
                    .parseSignedClaims(token) // 서명된 JWT만 파싱 가능
                    .getPayload(); // 토큰의 클레임 꺼냄
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 만료된 토큰에서 클레임 꺼내기
        }
    }

    public String getUsername(String token) {
        return getClaims(token).get("username", String.class);
    }
}
