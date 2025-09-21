package com.synclife.studyroom.user.controller;

import com.synclife.studyroom.common.dto.ResponseDto;
import com.synclife.studyroom.user.dto.TokenResponseDto;
import com.synclife.studyroom.user.dto.UserLoginDto;
import com.synclife.studyroom.user.dto.UserRequestDto;
import com.synclife.studyroom.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 API", description = "회원 로그인 및 회원가입 기능을 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "회원 로그인",
            description = "아이디와 비밀번호를 통해 로그인하고 JWT 토큰을 발급받습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "유저 로그인 성공"),
            @ApiResponse(responseCode = "400", description = "요청 값이 유효하지 않습니다. (G002)"),
            @ApiResponse(responseCode = "404", description = "해당 회원을 찾을 수 없습니다. (U001)"),
            @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않습니다. (U003)"),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다. (G001)")
    })
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

    @Operation(
            summary = "회원가입",
            description = "새로운 회원 정보를 등록합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "요청 값이 유효하지 않습니다. (G002)"),
            @ApiResponse(responseCode = "409", description = "이미 사용 중인 아이디입니다. (U002)"),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다. (G001)")
    })
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
