package com.example.realq.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류가 발생했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 유저 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.FORBIDDEN, "잘못된 비밀번호입니다."),

    // 인증 관련
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // 지역/대기질 관련
    REALTIME_AVERAGE_API_ERROR(HttpStatus.BAD_REQUEST, "시군구별 실시간 평균정보 조회 중 오류가 발생했습니다."),
    REGION_API_ERROR(HttpStatus.BAD_REQUEST, "시도별 실시간 측정정보 조회 중 오류가 발생했습니다."),
    STATION_API_ERROR(HttpStatus.BAD_REQUEST, "측정소별 실시간 측정정보 조회 중 오류가 발생했습니다."),
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 지역 정보를 찾을 수 없습니다."),
    STATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 측정소 정보를 찾을 수 없습니다."),
    AIR_QUALITY_NOT_AVAILABLE(HttpStatus.NOT_FOUND, "대기질 데이터가 없습니다."),

    // 알림 관련
    NOTIFICATION_REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 지역 알림입니다.") ,
    NOTIFICATION_STATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 측정소 알림입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.httpStatus = status;
        this.message = message;
    }
}
