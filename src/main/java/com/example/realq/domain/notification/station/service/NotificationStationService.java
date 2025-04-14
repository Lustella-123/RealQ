package com.example.realq.domain.notification.station.service;

import com.example.realq.domain.notification.station.dto.request.NotificationStationCreateRequest;
import com.example.realq.domain.notification.station.dto.request.NotificationStationPatchRequest;
import com.example.realq.domain.notification.station.dto.response.NotificationStationCreateResponse;
import com.example.realq.domain.notification.station.dto.response.NotificationStationPatchResponse;
import com.example.realq.domain.notification.station.dto.response.NotificationStationReadResponse;
import com.example.realq.domain.notification.station.entity.NotificationStation;
import com.example.realq.domain.notification.station.repository.NotificationStationRepository;
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
public class NotificationStationService {

    private final NotificationStationRepository notificationStationRepository;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    @Transactional
    public NotificationStationCreateResponse registerNotificationStation(
            NotificationStationCreateRequest requestDto,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        Long stationId = requestDto.stationId();
        Integer pm10Threshold = requestDto.pm10Threshold();
        Integer pm25Threshold = requestDto.pm25Threshold();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));


        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new GlobalException(ErrorCode.STATION_NOT_FOUND));

        Optional<NotificationStation> existingNotification = notificationStationRepository.findByUserEmailAndStationId(
                email,
                stationId
        );

        if (existingNotification.isPresent()) {
            return NotificationStationCreateResponse.toDto(existingNotification.get());
        }

        NotificationStation notificationStation = NotificationStation.toEntity(
                user,
                station,
                pm10Threshold,
                pm25Threshold
        );

        notificationStationRepository.save(notificationStation);

        return NotificationStationCreateResponse.toDto(notificationStation);
    }

    @Transactional(readOnly = true)
    public List<NotificationStationReadResponse> readAllNotificationStations(HttpSession session) {

        String email = (String) session.getAttribute("email");

        List<NotificationStation> notificationStationList = notificationStationRepository.findByUserEmail(email);

        return notificationStationList.stream().map(NotificationStationReadResponse::toDto).toList();
    }

    @Transactional
    public NotificationStationPatchResponse updateNotificationStation(
            NotificationStationPatchRequest requestDto,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        Long id = requestDto.id();

        userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        NotificationStation notificationStation = notificationStationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTIFICATION_STATION_NOT_FOUND));

        Integer newPm10Threshold = requestDto.newPm10Threshold();

        if (newPm10Threshold != null) {
            notificationStation.updatePm10Threshold(newPm10Threshold);
        }

        Integer newPm25Threshold = requestDto.newPm25Threshold();

        if(newPm25Threshold != null) {
            notificationStation.updatePm25Threshold(newPm25Threshold);
        }

        Boolean newEnabledStatus = requestDto.newEnabled();

        if (newEnabledStatus != null) {
            notificationStation.updateEnabledStatus(newEnabledStatus);
        }

        return NotificationStationPatchResponse.toDto(notificationStation);
    }

    @Transactional
    public void deleteNotificationStation(List<Long> notifiationPm10IdList) {

        notifiationPm10IdList.forEach(id -> {
            Optional<NotificationStation> existingNotificationPm10 = notificationStationRepository.findById(id);

            if (existingNotificationPm10.isEmpty()) {
                return;
            }

            notificationStationRepository.deleteById(id);
        });
    }
}
