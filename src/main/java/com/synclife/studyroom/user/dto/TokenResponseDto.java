package com.synclife.studyroom.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "로그인 성공 시 반환되는 토큰 응답 DTO")
public class TokenResponseDto {

    @Schema(description = "Access Token (JWT)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private final String accessToken;

    @Schema(description = "로그인한 사용자 ID", example = "42")
    private final Long userId;

}
