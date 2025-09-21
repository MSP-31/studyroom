package com.synclife.studyroom.user.service;

import com.synclife.studyroom.user.dto.TokenResponseDto;
import com.synclife.studyroom.user.dto.UserLoginDto;
import com.synclife.studyroom.user.dto.UserRequestDto;

public interface UserService {
    void createUser(UserRequestDto userRequestDto);

    TokenResponseDto login(UserLoginDto userLoginDto);
}
