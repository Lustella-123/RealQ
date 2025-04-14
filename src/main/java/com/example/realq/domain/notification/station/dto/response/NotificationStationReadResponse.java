package com.example.realq.domain.notification.station.dto.response;

import com.example.realq.domain.notification.station.entity.NotificationStation;

public record NotificationStationReadResponse(
        Long id,
        Long stationId,
        String stationName,
        int pm10Threshold,
        int pm25Threshold,
        boolean enabled
) {
    public static NotificationStationReadResponse toDto(NotificationStation notificationStation) {
        return new NotificationStationReadResponse(
                notificationStation.getId(),
                notificationStation.getStation().getId(),
                notificationStation.getStation().getName(),
                notificationStation.getPm10Threshold(),
                notificationStation.getPm25Threshold(),
                notificationStation.isEnabled()
        );
    }
}
