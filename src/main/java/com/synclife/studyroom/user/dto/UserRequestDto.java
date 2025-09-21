package com.synclife.studyroom.user.dto;

import com.synclife.studyroom.user.entity.UserRoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "사용자 생성 요청 DTO")
public class UserRequestDto {

    @Schema(description = "사용자 아이디 또는 이메일", example = "test")
    private final String username;

    @Schema(description = "사용자 비밀번호", example = "test")
    private final String password;

    @Schema(description = "사용자 역할", example = "ROLE_ADMIN")
    private final UserRoleType role;
}
