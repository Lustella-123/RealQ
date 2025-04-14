package com.example.realq.domain.notification.region.service;

import com.example.realq.domain.notification.region.dto.request.NotificationRegionCreateRequest;
import com.example.realq.domain.notification.region.dto.request.NotificationRegionPatchRequest;
import com.example.realq.domain.notification.region.dto.response.NotificationRegionCreateResponse;
import com.example.realq.domain.notification.region.dto.response.NotificationRegionPatchResponse;
import com.example.realq.domain.notification.region.dto.response.NotificationRegionReadResponse;
import com.example.realq.domain.notification.region.entity.NotificationRegion;
import com.example.realq.domain.notification.region.repository.NotificationRegionRepository;
import com.example.realq.domain.realtime.region.entity.Region;
import com.example.realq.domain.realtime.region.repository.RegionRepository;
import com.example.realq.domain.user.entity.User;
import com.example.realq.domain.user.repository.UserRepository;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationRegionService {

    private final NotificationRegionRepository notificationRegionRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;

    @Transactional
    public NotificationRegionCreateResponse registerNotificationRegion(
            NotificationRegionCreateRequest requestDto,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        Long regionId = requestDto.regionId();
        Integer pm10Threshold = requestDto.pm10Threshold();
        Integer pm25Threshold = requestDto.pm25Threshold();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new GlobalException(ErrorCode.STATION_NOT_FOUND));

        Optional<NotificationRegion> existingNotification = notificationRegionRepository.findByUserEmailAndRegionId(
                email,
                regionId
        );

        if (existingNotification.isPresent()) {
            return NotificationRegionCreateResponse.toDto(existingNotification.get());
        }

        NotificationRegion notificationRegion = NotificationRegion.toEntity(user, region, pm10Threshold, pm25Threshold);

        notificationRegionRepository.save(notificationRegion);

        return NotificationRegionCreateResponse.toDto(notificationRegion);
    }

    @Transactional(readOnly = true)
    public List<NotificationRegionReadResponse> readAllNotificationRegions(HttpSession session) {

        String email = (String) session.getAttribute("email");

        List<NotificationRegion> notificationRegionList = notificationRegionRepository.findByUserEmail(email);

        return notificationRegionList.stream().map(NotificationRegionReadResponse::toDto).toList();
    }

    @Transactional
    public NotificationRegionPatchResponse updateNotificationRegion(
            NotificationRegionPatchRequest requestDto,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        Long id = requestDto.id();

        userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        NotificationRegion notificationRegion = notificationRegionRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTIFICATION_PM10_NOT_FOUND));

        Integer newPm10Threshold = requestDto.newPm10Threshold();

        if (newPm10Threshold != null) {
            notificationRegion.updatePm10Threshold(newPm10Threshold);
        }

        Integer newPm25Threshold = requestDto.newPm25Threshold();

        if (newPm25Threshold != null) {
            notificationRegion.updatePm25Threshold(newPm25Threshold);
        }

        Boolean newEnabledStatus = requestDto.newEnabled();

        if (newEnabledStatus != null) {
            notificationRegion.updateEnabledStatus(newEnabledStatus);
        }

        return NotificationRegionPatchResponse.toDto(notificationRegion);
    }

    @Transactional
    public void deleteNotificationRegion(List<Long> notifiationRegionIdList) {

        notifiationRegionIdList.forEach(id -> {
            Optional<NotificationRegion> existingNotificationRegion = notificationRegionRepository.findById(id);

            if (existingNotificationRegion.isEmpty()) {
                return;
            }

            notificationRegionRepository.deleteById(id);
        });
    }
}
