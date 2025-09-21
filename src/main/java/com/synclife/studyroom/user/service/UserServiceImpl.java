package com.synclife.studyroom.user.service;

import com.synclife.studyroom.user.dto.TokenResponseDto;
import com.synclife.studyroom.user.dto.UserCreateDto;
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
    public TokenResponseDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당하는 이메일이 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다");
        }
        System.out.println("체크");

        return new TokenResponseDto(
                jwtProvider.createAccessToken(user.getId().toString(), user.getRole().toString()),
                // jwtProvider.createRefreshToken(user.getUserId().toString()),
                user.getId()
        );
    }

    @Override
    public void createUser(UserCreateDto userCreateDto) {
        userRepository.findByUsername(userCreateDto.getUsername())
                .ifPresent(user -> {
                    throw new RuntimeException("동일한 이름이 있습니다.");
                });
        userRepository.findByEmail(userCreateDto.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("이메일이 중복됩니다.");
                });

        User user = User.builder()
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .build();
        userRepository.save(user);
    }
}
