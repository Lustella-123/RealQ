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

    public UserSignUpResponse registerUser(UserSignUpRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = User.toEntity(userRequest.email(), userRequest.password(), userRequest.slackId());
        userRepository.save(user);
        return UserSignUpResponse.toDto();
    }

    public UserLoginResponse Login(UserLoginRequest userRequest, HttpSession session) {
        User user = userRepository.findByEmail(userRequest.email())
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        if (!user.getPassword().equals(userRequest.password())) {
            throw new GlobalException(ErrorCode.INVALID_PASSWORD);
        }

        session.setAttribute("email", user.getEmail());

        return UserLoginResponse.toDto();
    }
}
