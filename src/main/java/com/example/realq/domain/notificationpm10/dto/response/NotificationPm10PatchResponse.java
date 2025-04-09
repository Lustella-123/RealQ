package com.example.realq.domain.notificationpm10.dto.response;

import com.example.realq.domain.notificationpm10.entity.NotificationPm10;

public record NotificationPm10PatchResponse (
        Long id,
        Long stationId,
        String stationName,
        int updatedPm10Threshold,
        Boolean updatedEnabledStatus
) {
    public static NotificationPm10PatchResponse toDto(NotificationPm10 notificationPm10) {
        return new NotificationPm10PatchResponse(
                notificationPm10.getId(),
                notificationPm10.getStation().getId(),
                notificationPm10.getStation().getName(),
                notificationPm10.getPm10Threshold(),
                notificationPm10.isEnabled()
        );
    }
}
