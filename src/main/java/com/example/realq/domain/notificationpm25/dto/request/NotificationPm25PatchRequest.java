package com.example.realq.domain.notificationpm25.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record NotificationPm25PatchRequest(
        Long id,

        @Min(value = 1, message = "초미세먼지 PM2.5 최솟값은 1 이상이어야 합니다.")
        @Max(value = 150, message = "초미세먼지 PM2.5 최댓값은 150 이하이어야 합니다.")
        Integer newPm25Threshold,

        Boolean newEnabled
) {
}
