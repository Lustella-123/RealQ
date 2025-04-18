package com.example.realq.domain.slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.conversations.ConversationsOpenRequest;
import com.slack.api.methods.response.conversations.ConversationsOpenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackService {

    @Value("${SLACK_TOKEN}")
    private String botToken;

    private final Slack slack;

    @Async
    public void sendMessageToUser(String slackId, String message) {
        long start = System.nanoTime(); // 사용자별 시간 측정 시작

        try {
            // DM 채널 열기
            ConversationsOpenResponse openResponse = slack.methods(botToken)
                    .conversationsOpen(ConversationsOpenRequest.builder()
                            .users(List.of(slackId))
                            .build());

            if (openResponse.isOk()) {
                String channelId = openResponse.getChannel().getId();

                // 메시지 보내기
                slack.methods(botToken).chatPostMessage(ChatPostMessageRequest.builder()
                        .channel(channelId)
                        .text(message)
                        .build());

                long end = System.nanoTime();
                long durationS = (end - start) / 1_000_000_000;
                long durationMs = (end - start) / 1_000_000;
                log.info("✅ [전송 완료] {}: ({} s {} ms)", slackId, durationS, durationMs);
            } else {
                log.error("❌ DM 채널 열기 실패: {}", openResponse.getError());
            }
        } catch (IOException | SlackApiException e) {
            log.error("❌ 슬랙 메세지 전송 실패 - ID: {}, 메시지: {}", slackId, message, e);
        }
    }
}