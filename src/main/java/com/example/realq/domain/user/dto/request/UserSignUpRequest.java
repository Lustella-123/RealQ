package com.example.realq.domain.user.dto.request;

public record UserSignUpRequest(
        String email,
        String password,
        String slackId
) {
}
