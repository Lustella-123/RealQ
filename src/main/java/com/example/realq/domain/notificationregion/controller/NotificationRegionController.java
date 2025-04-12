package com.example.realq.domain.notificationregion.controller;

import com.example.realq.domain.notificationregion.dto.request.NotificationRegionCreateRequest;
import com.example.realq.domain.notificationregion.dto.request.NotificationRegionPatchRequest;
import com.example.realq.domain.notificationregion.dto.response.NotificationRegionCreateResponse;
import com.example.realq.domain.notificationregion.dto.response.NotificationRegionPatchResponse;
import com.example.realq.domain.notificationregion.dto.response.NotificationRegionReadResponse;
import com.example.realq.domain.notificationregion.service.NotificationRegionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/region/notifications")
@RequiredArgsConstructor
public class NotificationRegionController {

    private final NotificationRegionService notificationRegionService;

    @PostMapping
    public ResponseEntity<NotificationRegionCreateResponse> registerNotificationRegion(
            @Valid @RequestBody NotificationRegionCreateRequest requestDto,
            HttpSession session
    ) {
        NotificationRegionCreateResponse responseDto = notificationRegionService.registerNotificationRegion(requestDto, session);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationRegionReadResponse>> readAllNotificationRegions(HttpSession session) {

        List<NotificationRegionReadResponse> responseDtoList = notificationRegionService.readAllNotificationRegions(session);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<NotificationRegionPatchResponse> updateNotificationRegion(
            @Valid @RequestBody NotificationRegionPatchRequest requestDto,
            HttpSession session
    ) {
        NotificationRegionPatchResponse responseDto = notificationRegionService.updateNotificationRegion(requestDto, session);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNotificationRegion(@RequestParam List<Long> notificationRegionIdList) {

        notificationRegionService.deleteNotificationRegion(notificationRegionIdList);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
