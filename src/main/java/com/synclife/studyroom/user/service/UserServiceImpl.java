package com.synclife.studyroom.user.service;

import com.synclife.studyroom.user.dto.TokenResponseDto;
import com.synclife.studyroom.user.dto.UserLoginDto;
import com.synclife.studyroom.user.dto.UserRequestDto;
import com.synclife.studyroom.user.entity.User;
import com.synclife.studyroom.user.jwt.JwtTokenProvider;
import com.synclife.studyroom.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;

    /**
     * 아이디와 비밀번호를 입력받아 엑세스 토큰을 발급하는 메서드
     * @param userLoginDto 로그인에 필요한 아이디/비밀번호 정보
     * @return 엑세스토큰과 유저ID가 담긴 DTO
     */
    @Override
    public TokenResponseDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("해당하는 이메일이 없습니다."));

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다");
        }

        return new TokenResponseDto(
                jwtProvider.createAccessToken(user.getUsername(), user.getRole().toString()),
                // jwtProvider.createRefreshToken(user.getUserId().toString()),
                user.getId()
        );
    }

    /**
     * 계정 생성 메서드
     * @param userRequestDto 계정 생성에 필요한 아이디/비밀번호/권한 정보
     */
    @Override
    public void createUser(UserRequestDto userRequestDto) {
        userRepository.findByUsername(userRequestDto.getUsername())
                .ifPresent(user -> {
                    throw new RuntimeException("동일한 이름이 있습니다.");
                });

        User user = User.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(userRequestDto.getRole())
                .build();
        userRepository.save(user);
    }
}
