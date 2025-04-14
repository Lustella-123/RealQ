package com.example.realq.domain.notification.station.controller;

import com.example.realq.domain.notification.station.dto.request.NotificationStationCreateRequest;
import com.example.realq.domain.notification.station.dto.request.NotificationStationPatchRequest;
import com.example.realq.domain.notification.station.dto.response.NotificationStationCreateResponse;
import com.example.realq.domain.notification.station.dto.response.NotificationStationPatchResponse;
import com.example.realq.domain.notification.station.dto.response.NotificationStationReadResponse;
import com.example.realq.domain.notification.station.service.NotificationStationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/station/notifications")
@RequiredArgsConstructor
public class NotificationStationController {

    private final NotificationStationService notificationStationService;

    @PostMapping
    public ResponseEntity<NotificationStationCreateResponse> registerNotificationStation(
            @Valid @RequestBody NotificationStationCreateRequest requestDto,
            HttpSession session
    ) {
        NotificationStationCreateResponse responseDto = notificationStationService.registerNotificationStation(
                requestDto,
                session
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationStationReadResponse>> readAllNotificationStations(HttpSession session) {

        List<NotificationStationReadResponse> responseDtoList = notificationStationService.readAllNotificationStations(session);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<NotificationStationPatchResponse> updateNotificationStation(
            @Valid @RequestBody NotificationStationPatchRequest requestDto,
            HttpSession session
    ) {
        NotificationStationPatchResponse responseDto = notificationStationService.updateNotificationStation(
                requestDto,
                session
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNotificationStation(@RequestParam List<Long> notificationStationIdList) {

        notificationStationService.deleteNotificationStation(notificationStationIdList);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
