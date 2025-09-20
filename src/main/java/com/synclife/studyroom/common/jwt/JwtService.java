package com.synclife.studyroom.common.jwt;

import com.synclife.studyroom.user.entity.User;
import com.synclife.studyroom.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;

    /**
     * 생성자: JWT 비밀키를 설정하고, 필요한 서비스들을 주입받음
     * @param secret         JWT 서명용 비밀키 (application.yml에서 설정)
     * @param userRepository 사용자 정보를 조회하기 위한 UserRepository 인스턴스
     */
    public JwtService(@Value("${jwt.secret}") String secret, UserRepository userRepository) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        this.userRepository = userRepository;
    }

    /**
     * 엑세스 토큰을 생성하는 메서드
     * @param claims     공개 클레임
     * @param expiration 토큰의 수명
     * @return           생성된 토큰을 반환
     */
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

    /**
     * 엑세스 토큰의 만료 여부를 반환하는 메서드
     * @param token 엑세스 토큰
     * @return True/False
     */
    public boolean validateToken(String token) {
        return !getClaims(token).getExpiration().before(new Date());
    }

    /**
     * 엑세스 토큰에서 클레임을 꺼내는 메서드
     * @param token 엑세스 토큰
     * @return 추출된 클레임을 반환
     */
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

    /**
     * 현재 로그인한 유저의 이름을 가져오는 메서드
     * @param token 엑세스 토큰
     * @return 엑세스 토큰에서 분리한 유저 이름을 반환
     */
    public String getUsername(String token) {
        return getClaims(token).get("username", String.class);
    }

    /**
     * 현재 로그인한 유저의 정보를 가져오는 메서드
     * @return (User) 현재 유저의 정보 반환
     */
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당하는 유저가 없습니다."));
    }
}
