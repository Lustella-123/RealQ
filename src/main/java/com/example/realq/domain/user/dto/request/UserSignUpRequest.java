package com.example.realq.domain.user.dto.request;

public record UserSignUpRequest(
        String slackId,
        String password
) {
}
