package com.example.realq.domain.notificationregion.dto.response;

import com.example.realq.domain.notificationregion.entity.NotificationRegion;

public record NotificationRegionReadResponse(
        Long id,
        Long regionId,
        String regionName,
        int pm10Threshold,
        int pm25Threshold
) {
    public static NotificationRegionReadResponse toDto(NotificationRegion notificationRegion) {
        return new NotificationRegionReadResponse(
                notificationRegion.getId(),
                notificationRegion.getRegion().getId(),
                notificationRegion.getRegion().getName(),
                notificationRegion.getPm10Threshold(),
                notificationRegion.getPm25Threshold()
        );
    }
}
