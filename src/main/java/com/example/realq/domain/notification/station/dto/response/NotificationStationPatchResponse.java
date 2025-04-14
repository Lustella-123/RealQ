package com.example.realq.domain.notification.station.dto.response;

import com.example.realq.domain.notification.station.entity.NotificationStation;

public record NotificationStationPatchResponse(
        Long id,
        Long stationId,
        String stationName,
        int updatedPm10Threshold,
        int updatedPm25Threshold,
        Boolean updatedEnabledStatus
) {
    public static NotificationStationPatchResponse toDto(NotificationStation notificationStation) {
        return new NotificationStationPatchResponse(
                notificationStation.getId(),
                notificationStation.getStation().getId(),
                notificationStation.getStation().getName(),
                notificationStation.getPm10Threshold(),
                notificationStation.getPm25Threshold(),
                notificationStation.isEnabled()
        );
    }
}
