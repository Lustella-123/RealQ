package com.example.realq.domain.user.service;

import com.example.realq.domain.user.dto.request.UserLoginRequest;
import com.example.realq.domain.user.dto.request.UserSignUpRequest;
import com.example.realq.domain.user.dto.response.UserLoginResponse;
import com.example.realq.domain.user.dto.response.UserSignUpResponse;
import com.example.realq.domain.user.entity.User;
import com.example.realq.domain.user.repository.UserRepository;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSignUpResponse registerUser(UserSignUpRequest requestDto) {
        if (userRepository.existsBySlackId(requestDto.slackId())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = User.toEntity(requestDto.slackId(), requestDto.password());
        userRepository.save(user);
        return UserSignUpResponse.toDto();
    }

    public UserLoginResponse Login(UserLoginRequest requestDto, HttpSession session) {
        User user = userRepository.findBySlackId(requestDto.slackId())
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        if (!user.getPassword().equals(requestDto.password())) {
            throw new GlobalException(ErrorCode.INVALID_PASSWORD);
        }

        session.setAttribute("slackId", user.getSlackId());

        return UserLoginResponse.toDto();
    }
}
