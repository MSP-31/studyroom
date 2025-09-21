package com.synclife.studyroom.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "사용자 로그인 요청 DTO")
public class UserLoginDto {

    @Schema(description = "사용자 아이디", example = "test")
    private final String username;

    @Schema(description = "사용자 비밀번호", example = "test")
    private final String password;
}
