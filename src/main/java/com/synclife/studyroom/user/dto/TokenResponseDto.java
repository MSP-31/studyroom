package com.synclife.studyroom.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 성공 시 반환되는 토큰 응답 DTO")
public record TokenResponseDto(
        @Schema(description = "Access Token (JWT)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String accessToken,
        @Schema(description = "로그인한 사용자 ID", example = "42") Long userId) {
}
