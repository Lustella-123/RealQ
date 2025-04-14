package com.example.realq.domain.notification.station.dto.response;

import com.example.realq.domain.notification.station.entity.NotificationStation;

public record NotificationStationCreateResponse(
        Long id,
        Long stationId,
        String stationName,
        int pm10Threshold,
        int pm25Threshold
) {
    public static NotificationStationCreateResponse toDto(NotificationStation notificationStation) {
        return new NotificationStationCreateResponse(
                notificationStation.getId(),
                notificationStation.getStation().getId(),
                notificationStation.getStation().getName(),
                notificationStation.getPm10Threshold(),
                notificationStation.getPm25Threshold()
        );
    }
}