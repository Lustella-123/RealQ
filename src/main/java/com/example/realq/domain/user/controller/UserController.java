package com.example.realq.domain.user.controller;

import com.example.realq.domain.user.dto.request.UserLoginRequest;
import com.example.realq.domain.user.dto.request.UserSignUpRequest;
import com.example.realq.domain.user.dto.response.UserLoginResponse;
import com.example.realq.domain.user.dto.response.UserSignUpResponse;
import com.example.realq.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signup(@RequestBody UserSignUpRequest userRequest) {

        UserSignUpResponse userSignUpResponse = userService.registerUser(userRequest);

        return new ResponseEntity<>(userSignUpResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            @RequestBody UserLoginRequest userRequest,
            HttpSession session
    ) {
        UserLoginResponse userLoginResponse = userService.Login(userRequest, session);

        return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
    }
}
