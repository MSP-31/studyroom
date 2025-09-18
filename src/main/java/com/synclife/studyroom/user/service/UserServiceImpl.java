package com.synclife.studyroom.user.service;

import com.synclife.studyroom.user.dto.UserCreateDto;
import com.synclife.studyroom.user.dto.UserResponseDto;
import com.synclife.studyroom.user.entity.User;
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

    @Override
    public void createUser(UserCreateDto userCreateDto) {
        userRepository.findByName(userCreateDto.getName())
                .ifPresent(user -> {
                    throw new RuntimeException("동일한 이름이 있습니다.");
                });
        userRepository.findByEmail(userCreateDto.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("이메일이 중복됩니다.");
                });

        User user = User.builder()
                .name(userCreateDto.getName())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .build();
        userRepository.save(user);
    }
}
