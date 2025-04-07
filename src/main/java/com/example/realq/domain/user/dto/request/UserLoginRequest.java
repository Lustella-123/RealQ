package com.example.realq.domain.user.dto.request;

public record UserLoginRequest (
        String email,
        String password
){
}
