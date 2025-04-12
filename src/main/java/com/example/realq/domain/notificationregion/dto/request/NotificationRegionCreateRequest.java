package com.example.realq.domain.notificationregion.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NotificationRegionCreateRequest(
        Long regionId,

        @NotNull(message = "미세먼지 PM10 기준치는 필수 입력입니다.")
        @Min(value = 1, message = "미세먼지 PM10 최솟값은 1 이상이어야 합니다.")
        @Max(value = 300, message = "미세먼지 PM10 최댓값은 300 이하이어야 합니다.")
        Integer pm10Threshold,

        @NotNull(message = "초미세먼지 PM2.5 기준치는 필수 입력입니다.")
        @Min(value = 1, message = "초미세먼지 PM2.5 최솟값은 1 이상이어야 합니다.")
        @Max(value = 150, message = "초미세먼지 PM2.5 최댓값은 300 이하이어야 합니다.")
        Integer pm25Threshold
) {
}
