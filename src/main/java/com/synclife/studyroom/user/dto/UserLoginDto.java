package com.synclife.studyroom.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginDto {

    private final String username;

    private final String password;
}
