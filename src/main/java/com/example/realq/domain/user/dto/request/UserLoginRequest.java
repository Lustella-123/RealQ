package com.example.realq.domain.user.dto.request;

public record UserLoginRequest (
        String slackId,
        String password
){
}
