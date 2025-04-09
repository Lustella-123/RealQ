package com.example.realq.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private List<FieldErrorDetail> fieldErrorDetailList;

    public static ErrorResponse of(int status, String error, String message) {
        return new ErrorResponse(status, error, message, LocalDateTime.now(), null);
    }

    public static ErrorResponse of(int status, String error, String message, List<FieldErrorDetail> fieldErrorDetailList) {
        return new ErrorResponse(status, error, message, LocalDateTime.now(), fieldErrorDetailList);
    }
}