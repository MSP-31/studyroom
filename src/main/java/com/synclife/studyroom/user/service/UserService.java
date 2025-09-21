package com.synclife.studyroom.user.service;

import com.synclife.studyroom.user.dto.TokenResponseDto;
import com.synclife.studyroom.user.dto.UserCreateDto;

public interface UserService {
    void createUser(UserCreateDto userCreateDto);

    TokenResponseDto login(String email, String password);
}
