package com.example.realq.domain.notificationpm10.dto.response;

import com.example.realq.domain.notificationpm10.entity.NotificationPm10;

public record NotificationPm10CreateResponse(
        Long id,
        Long stationId,
        String stationName,
        int pm10Thread
) {
    public static NotificationPm10CreateResponse toDto(NotificationPm10 notificationPm10) {
        return new NotificationPm10CreateResponse(
                notificationPm10.getId(),
                notificationPm10.getStation().getId(),
                notificationPm10.getStation().getName(),
                notificationPm10.getPm10Threshold()
        );
    }
}
