package com.example.realq.domain.notificationpm10.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NotificationPm10CreateRequest(
        Long stationId,

        @NotNull(message = "미세먼지 PM10 기준치는 필수 입력입니다.")
        @Min(value = 1, message = "미세먼지 PM10 최솟값은 1 이상이어야 합니다.")
        @Max(value = 300, message = "미세먼지 PM10 최댓값은 300 이하이어야 합니다.")
        Integer pm10Threshold
) {
}