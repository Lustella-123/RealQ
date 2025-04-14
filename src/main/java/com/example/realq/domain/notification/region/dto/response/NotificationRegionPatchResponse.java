package com.example.realq.domain.notification.region.dto.response;

import com.example.realq.domain.notification.region.entity.NotificationRegion;

public record NotificationRegionPatchResponse(
        Long id,
        Long regionId,
        String regionName,
        int updatedPm10Threshold,
        int updatedPm25Threshold,
        Boolean updatedEnabledStatus
) {
    public static NotificationRegionPatchResponse toDto(NotificationRegion notificationRegion) {
        return new NotificationRegionPatchResponse(
                notificationRegion.getId(),
                notificationRegion.getRegion().getId(),
                notificationRegion.getRegion().getName(),
                notificationRegion.getPm10Threshold(),
                notificationRegion.getPm25Threshold(),
                notificationRegion.isEnabled()
        );
    }
}
