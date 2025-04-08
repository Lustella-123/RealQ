package com.example.realq.domain.notificationpm10.controller;

import com.example.realq.domain.notificationpm10.dto.request.NotificationPm10CreateRequest;
import com.example.realq.domain.notificationpm10.dto.request.NotificationPm10PatchRequest;
import com.example.realq.domain.notificationpm10.dto.response.NotificationPm10CreateResponse;
import com.example.realq.domain.notificationpm10.dto.response.NotificationPm10PatchResponse;
import com.example.realq.domain.notificationpm10.dto.response.NotificationPm10ReadResponse;
import com.example.realq.domain.notificationpm10.service.NotificationPm10Service;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pm10/notifications")
@RequiredArgsConstructor
public class NotificationPm10Controller {

    private final NotificationPm10Service notificationPm10Service;

    @PostMapping
    public ResponseEntity<NotificationPm10CreateResponse> registerNotificationPm10(
            @Valid @RequestBody NotificationPm10CreateRequest requestDto,
            HttpSession session
    ) {
        NotificationPm10CreateResponse responseDto = notificationPm10Service.registerNotificationPm10(requestDto, session);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationPm10ReadResponse>> readAllNotificationPm10s(HttpSession session) {

        List<NotificationPm10ReadResponse> responseDtoList = notificationPm10Service.readAllNotificationPm10s(session);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<NotificationPm10PatchResponse> updateNotificationPm10(
            @Valid @RequestBody NotificationPm10PatchRequest requestDto,
            HttpSession session
    ) {
        NotificationPm10PatchResponse responseDto = notificationPm10Service.updateNotificationPm10(requestDto, session);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNotificationPm10(@RequestParam List<Long> notificationPm10IdList) {

        notificationPm10Service.deleteNotificationPm10(notificationPm10IdList);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
