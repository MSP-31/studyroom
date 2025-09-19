package com.synclife.studyroom.user.jwt;

import com.synclife.studyroom.common.jwt.JwtService;
import com.synclife.studyroom.user.entity.UserRoleType;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private static final long ACCESS_TOKEN_EXP = 1000L * 60L * 30L; // 30분

    /**
     * 생성자: JWT 비밀키를 설정하고, 필요한 서비스들을 주입받음
     * @param secret            JWT 서명용 비밀키 (application.yml에서 설정)
     * @param jwtService        JWT 공통 서비스
     * @param userDetailsService 사용자 정보를 가져오는 서비스
     */
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 엑세스 토큰 생성 메서드
     * @param username 사용자 이름
     * @param role     사용자 권한
     * @return 생성된 엑세스 토큰
     */
    public String createAccessToken(String username, String role) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("role", role);

        return jwtService.generateToken(claims, ACCESS_TOKEN_EXP);
    }

    /**
     * 토큰 만료여부 확인
     * @param token 검증할 JWT
     * @return 유효하면 true 만료는 false 반환
     */
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public String getUsername(String token) {
        return jwtService.getUsername(token);
    }
}
