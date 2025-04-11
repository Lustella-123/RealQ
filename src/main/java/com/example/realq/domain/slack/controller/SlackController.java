package com.example.realq.domain.slack.controller;

import com.example.realq.domain.slack.service.SlackUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/slack/notifications")
@RequiredArgsConstructor
public class SlackController {

    private final SlackUserProvider slackUserProvider;

    @PostMapping
    public ResponseEntity<String> sendNotifications() {
        slackUserProvider.sendNotificationToAllUsers();

        return ResponseEntity.ok("알림 보냄!");

    }
}
