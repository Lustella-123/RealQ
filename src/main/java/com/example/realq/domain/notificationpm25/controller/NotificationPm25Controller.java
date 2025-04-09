package com.example.realq.domain.notificationpm25.controller;

import com.example.realq.domain.notificationpm25.dto.request.NotificationPm25CreateRequest;
import com.example.realq.domain.notificationpm25.dto.request.NotificationPm25PatchRequest;
import com.example.realq.domain.notificationpm25.dto.response.NotificationPm25CreateResponse;
import com.example.realq.domain.notificationpm25.dto.response.NotificationPm25PatchResponse;
import com.example.realq.domain.notificationpm25.dto.response.NotificationPm25ReadResponse;
import com.example.realq.domain.notificationpm25.service.NotificationPm25Service;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pm25/notifications")
@RequiredArgsConstructor
public class NotificationPm25Controller {

    private final NotificationPm25Service notificationPm25Service;

    @PostMapping
    public ResponseEntity<NotificationPm25CreateResponse> registerNotificationPm25(
            @Valid @RequestBody NotificationPm25CreateRequest requestDto,
            HttpSession session
    ) {
        NotificationPm25CreateResponse responseDto = notificationPm25Service.registerNotificationPm25(requestDto, session);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationPm25ReadResponse>> readAllNotificationPm25s(HttpSession session) {

        List<NotificationPm25ReadResponse> responseDtoList = notificationPm25Service.readAllNotificationPm25s(session);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<NotificationPm25PatchResponse> updateNotificationPm25(
            @Valid @RequestBody NotificationPm25PatchRequest requestDto,
            HttpSession session
    ) {
        NotificationPm25PatchResponse responseDto = notificationPm25Service.updateNotificationPm25(requestDto, session);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNotificationPm25(@RequestParam List<Long> notificationPm25IdList) {

        notificationPm25Service.deleteNotificationPm25(notificationPm25IdList);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
