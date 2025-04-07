package com.example.realq.domain.user.dto.response;

public record UserLoginResponse(
        String message
) {
    public static UserLoginResponse toDto() {
        return new UserLoginResponse("로그인이 완료되었습니다.");
    }
}
