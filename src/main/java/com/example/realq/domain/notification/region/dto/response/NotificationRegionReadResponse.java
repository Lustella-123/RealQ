package com.example.realq.domain.notification.region.dto.response;

import com.example.realq.domain.notification.region.entity.NotificationRegion;

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
