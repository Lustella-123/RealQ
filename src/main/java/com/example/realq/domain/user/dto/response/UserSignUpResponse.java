package com.example.realq.domain.user.dto.response;

public record UserSignUpResponse (
        String message
) {
    public static UserSignUpResponse toDto() {
        return new UserSignUpResponse("회원가입이 완료되었습니다.");
    }
}
