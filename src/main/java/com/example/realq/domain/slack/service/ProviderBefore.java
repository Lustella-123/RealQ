package com.example.realq.domain.slack.service;

import com.example.realq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProviderBefore {

    private final UserRepository userRepository;
    private final ServiceBefore serviceBefore;

    @Scheduled(cron = "0 * * * * *")
    public void sendNotificationToAllUsers() {
        List<String> slackIdList = userRepository.findAllSlackIdList();

        String message = "테스트";

        serviceBefore.sendMessageToAllUsers(slackIdList, message);
    }
}