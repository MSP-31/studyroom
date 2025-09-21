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
