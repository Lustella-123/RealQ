package com.example.realq.domain.notificationpm10.dto.response;

import com.example.realq.domain.notificationpm10.entity.NotificationPm10;

public record NotificationPm10ReadResponse (
        Long id,
        Long stationId,
        String stationName,
        int pm10Threshold
) {
    public static NotificationPm10ReadResponse toDto(NotificationPm10 notificationPm10) {
        return new NotificationPm10ReadResponse(
                notificationPm10.getId(),
                notificationPm10.getStation().getId(),
                notificationPm10.getStation().getName(),
                notificationPm10.getPm10Threshold()
        );
    }
}
