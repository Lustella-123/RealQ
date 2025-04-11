package com.example.realq.domain.notificationpm10.service;

import com.example.realq.domain.notificationpm10.dto.request.NotificationPm10CreateRequest;
import com.example.realq.domain.notificationpm10.dto.request.NotificationPm10PatchRequest;
import com.example.realq.domain.notificationpm10.dto.response.NotificationPm10CreateResponse;
import com.example.realq.domain.notificationpm10.dto.response.NotificationPm10PatchResponse;
import com.example.realq.domain.notificationpm10.dto.response.NotificationPm10ReadResponse;
import com.example.realq.domain.notificationpm10.entity.NotificationPm10;
import com.example.realq.domain.notificationpm10.repository.NotificationPm10Repository;
import com.example.realq.domain.realtime.station.entity.Station;
import com.example.realq.domain.realtime.station.repository.StationRepository;
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
public class NotificationPm10Service {

    private final NotificationPm10Repository notificationPm10Repository;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    @Transactional
    public NotificationPm10CreateResponse registerNotificationPm10(
            NotificationPm10CreateRequest requestDto,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        Long stationId = requestDto.stationId();
        Integer pm10Threshold = requestDto.pm10Threshold();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));


        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new GlobalException(ErrorCode.STATION_NOT_FOUND));

        Optional<NotificationPm10> existingNotification = notificationPm10Repository.findByUserEmailAndStationId(
                email,
                stationId
        );

        if (existingNotification.isPresent()) {
            return NotificationPm10CreateResponse.toDto(existingNotification.get());
        }

        NotificationPm10 notificationPm10 = NotificationPm10.toEntity(user, station, pm10Threshold);

        notificationPm10Repository.save(notificationPm10);

        return NotificationPm10CreateResponse.toDto(notificationPm10);
    }

    @Transactional(readOnly = true)
    public List<NotificationPm10ReadResponse> readAllNotificationPm10s(HttpSession session) {

        String email = (String) session.getAttribute("email");

        List<NotificationPm10> notificationPm10List = notificationPm10Repository.findByUserEmail(email);

        return notificationPm10List.stream().map(NotificationPm10ReadResponse::toDto).toList();
    }

    @Transactional
    public NotificationPm10PatchResponse updateNotificationPm10(
            NotificationPm10PatchRequest requestDto,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        Long id = requestDto.id();

        userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        NotificationPm10 notificationPm10 = notificationPm10Repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTIFICATION_PM10_NOT_FOUND));

        Integer newPm10Threshold = requestDto.newPm10Threshold();

        if (newPm10Threshold != null) {
            notificationPm10.updatePm10Threshold(newPm10Threshold);
        }

        Boolean newEnabledStatus = requestDto.newEnabled();

        if (newEnabledStatus != null) {
            notificationPm10.updateEnabledStatus(newEnabledStatus);
        }

        return NotificationPm10PatchResponse.toDto(notificationPm10);
    }

    @Transactional
    public void deleteNotificationPm10(List<Long> notifiationPm10IdList) {

        notifiationPm10IdList.forEach(id -> {
            Optional<NotificationPm10> existingNotificationPm10 = notificationPm10Repository.findById(id);

            if (existingNotificationPm10.isEmpty()) {
                return;
            }

            notificationPm10Repository.deleteById(id);
        });
    }
}
