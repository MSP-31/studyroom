package com.synclife.studyroom.user.service;

import com.synclife.studyroom.common.exception.exceptions.CustomException;
import com.synclife.studyroom.common.exception.messages.ExceptionMessage;
import com.synclife.studyroom.user.dto.CustomUserDetails;
import com.synclife.studyroom.user.entity.User;
import com.synclife.studyroom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * 유저 이름을 토대로 유저 정보를 반환
     * @param username 유저 정보를 얻기위한 유저 이름
     * @return User 정보
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->  new CustomException(ExceptionMessage.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}
