package com.example.realq.domain.notificationpm25.service;

import com.example.realq.domain.notificationpm25.dto.request.NotificationPm25CreateRequest;
import com.example.realq.domain.notificationpm25.dto.request.NotificationPm25PatchRequest;
import com.example.realq.domain.notificationpm25.dto.response.NotificationPm25CreateResponse;
import com.example.realq.domain.notificationpm25.dto.response.NotificationPm25PatchResponse;
import com.example.realq.domain.notificationpm25.dto.response.NotificationPm25ReadResponse;
import com.example.realq.domain.notificationpm25.entity.NotificationPm25;
import com.example.realq.domain.notificationpm25.repository.NotificationPm25Repository;
import com.example.realq.domain.station.entity.Station;
import com.example.realq.domain.station.repository.StationRepository;
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
public class NotificationPm25Service {

    private final NotificationPm25Repository notificationPm25Repository;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    @Transactional
    public NotificationPm25CreateResponse registerNotificationPm25(
            NotificationPm25CreateRequest requestDto,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        Long stationId = requestDto.stationId();
        Integer pm25Threshold = requestDto.pm25Threshold();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));


        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new GlobalException(ErrorCode.STATION_NOT_FOUND));

        Optional<NotificationPm25> existingNotification = notificationPm25Repository.findByUserEmailAndStationId(
                email,
                stationId
        );

        if (existingNotification.isPresent()) {
            return NotificationPm25CreateResponse.toDto(existingNotification.get());
        }

        NotificationPm25 notificationPm25 = NotificationPm25.toEntity(user, station, pm25Threshold);

        notificationPm25Repository.save(notificationPm25);

        return NotificationPm25CreateResponse.toDto(notificationPm25);
    }

    @Transactional(readOnly = true)
    public List<NotificationPm25ReadResponse> readAllNotificationPm25s(HttpSession session) {

        String email = (String) session.getAttribute("email");

        List<NotificationPm25> notificationPm25List = notificationPm25Repository.findByUserEmail(email);

        return notificationPm25List.stream().map(NotificationPm25ReadResponse::toDto).toList();
    }

    @Transactional
    public NotificationPm25PatchResponse updateNotificationPm25(
            NotificationPm25PatchRequest requestDto,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("email");
        Long id = requestDto.id();

        userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        NotificationPm25 notificationPm25 = notificationPm25Repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTIFICATION_PM25_NOT_FOUND));

        Integer newPm25Threshold = requestDto.newPm25Threshold();

        if (newPm25Threshold != null) {
            notificationPm25.updatePm25Threshold(newPm25Threshold);
        }

        Boolean newEnabledStatus = requestDto.newEnabled();

        if (newEnabledStatus != null) {
            notificationPm25.updateEnabledStatus(newEnabledStatus);
        }

        return NotificationPm25PatchResponse.toDto(notificationPm25);
    }

    @Transactional
    public void deleteNotificationPm25(List<Long> notifiationPm25IdList) {

        notifiationPm25IdList.forEach(id -> {
            Optional<NotificationPm25> existingNotificationPm25 = notificationPm25Repository.findById(id);

            if (existingNotificationPm25.isEmpty()) {
                return;
            }

            notificationPm25Repository.deleteById(id);
        });
    }
}
