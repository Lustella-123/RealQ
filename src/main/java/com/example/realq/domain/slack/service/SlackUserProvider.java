package com.example.realq.domain.slack.service;

import com.example.realq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SlackUserProvider {

    private final UserRepository userRepository;
    private final SlackService slackService;

    public void sendNotificationToAllUsers() {
        List<String> slackIdList = userRepository.findAllSlackIdList();

        String message = "테스트";

        slackService.sendMessageToAllUsers(slackIdList, message);
    }
}
