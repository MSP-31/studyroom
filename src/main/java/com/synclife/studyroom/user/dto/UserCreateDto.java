package com.synclife.studyroom.user.dto;

import com.synclife.studyroom.user.entity.UserRoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateDto {

    private String name;

    private String email;

    private String password;
}