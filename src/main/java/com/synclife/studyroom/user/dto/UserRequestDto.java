package com.synclife.studyroom.user.dto;

import com.synclife.studyroom.user.entity.UserRoleType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 생성 요청 DTO")
public record UserRequestDto(
        @Schema(description = "사용자 아이디 또는 이메일", example = "test") String username,
        @Schema(description = "사용자 비밀번호", example = "test") String password,
        @Schema(description = "사용자 역할", example = "ROLE_ADMIN") UserRoleType role) {

}
