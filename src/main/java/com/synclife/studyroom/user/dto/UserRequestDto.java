package com.synclife.studyroom.user.dto;

import com.synclife.studyroom.user.entity.UserRoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    private String username;

    private String password;

    private UserRoleType role;
}
