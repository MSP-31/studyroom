package com.synclife.studyroom.user.controller;

import com.synclife.studyroom.common.dto.ResponseDto;
import com.synclife.studyroom.user.dto.TokenResponseDto;
import com.synclife.studyroom.user.dto.UserLoginDto;
import com.synclife.studyroom.user.dto.UserRequestDto;
import com.synclife.studyroom.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<TokenResponseDto>> loginUser(@RequestBody @Valid UserLoginDto userLoginDto){
        TokenResponseDto tokenResponseDto = userService.login(userLoginDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "유저 로그인 성공",
                        tokenResponseDto
                )
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Void>> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "회원가입 성공",
                        null
                )
        );
    }
}
