package com.synclife.studyroom.user.dto;

import com.synclife.studyroom.user.entity.UserRoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {

    private final String username;

    private final String password;

    private final UserRoleType role;
}
