package com.synclife.studyroom.user.dto;

import com.synclife.studyroom.user.entity.UserRoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {
    private final Long userId;

    private final String username;

    private final String email;

    private final String password;

    private final UserRoleType role;
}
