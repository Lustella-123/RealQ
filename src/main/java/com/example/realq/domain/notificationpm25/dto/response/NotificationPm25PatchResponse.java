package com.example.realq.domain.notificationpm25.dto.response;

import com.example.realq.domain.notificationpm25.entity.NotificationPm25;

public record NotificationPm25PatchResponse(
        Long id,
        Long stationId,
        String stationName,
        int pm25Threshold
) {
    public static NotificationPm25PatchResponse toDto(NotificationPm25 notificationPm25) {
        return new NotificationPm25PatchResponse(
                notificationPm25.getId(),
                notificationPm25.getStation().getId(),
                notificationPm25.getStation().getName(),
                notificationPm25.getPm25Threshold()
        );
    }
}
