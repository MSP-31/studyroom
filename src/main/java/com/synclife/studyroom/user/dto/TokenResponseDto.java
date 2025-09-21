package com.synclife.studyroom.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponseDto {

    private String accessToken;

    // private String refreshToken;

    private Long userId;

    public TokenResponseDto(String accessToken, Long id) {
        this.accessToken = accessToken;
        this.userId = id;
    }
}
