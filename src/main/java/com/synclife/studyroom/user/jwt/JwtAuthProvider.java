package com.synclife.studyroom.user.jwt;

import com.synclife.studyroom.common.jwt.JwtService;
import com.synclife.studyroom.user.entity.UserRoleType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthProvider {
    private final JwtService jwtService;

    private static final long ACCESS_TOKEN_EXP = 1000L * 60L * 60L; // 1시간

    public JwtAuthProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String createAccessToken(String sub, UserRoleType role) {
        Map<String, String> claims = new HashMap<>();
        claims.put("sub", sub); // 사용자 식별자
        claims.put("role", role.getValue()); // 권한 정보
        return jwtService.generateToken(claims, ACCESS_TOKEN_EXP);
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    // 토큰 유효성 검사
    public boolean isValidAccessToken(String accessToken) {
        return  accessToken != null
                && jwtService.validateToken(accessToken);
    }
}
