package com.synclife.studyroom.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 로그인 요청 DTO")
public record UserLoginDto(
        @Schema(description = "사용자 아이디", example = "test") String username,
        @Schema(description = "사용자 비밀번호", example = "test") String password) {

}
