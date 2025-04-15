package com.example.realq.domain.client.region;

import com.example.realq.domain.average.region.entity.AverageRegion;
import com.example.realq.domain.notification.region.entity.NotificationRegion;
import com.example.realq.domain.notification.region.repository.NotificationRegionRepository;
import com.example.realq.domain.slack.SlackService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RegionSendClient {

    private final SlackService slackService;
    private final RegionGetClient regionGetClient;
    private final NotificationRegionRepository notificationRegionRepository;

    @Scheduled(cron = "0 0 7,12,17 * * *")
    public void sendAverageRegion() {
        log.info("메서드 실행: sendAverageRegion");

        long totalStart = System.nanoTime(); // 전체 시간 측정 시작

        List<AverageRegion> averageRegionList = regionGetClient.getAverageRegion();
        List<NotificationRegion> notificationRegionList = notificationRegionRepository.findAllByEnabledTrue();

        Map<String, String> userSlackIdToMessage = fetchAlertTargets(notificationRegionList, averageRegionList);

        userSlackIdToMessage.forEach(slackService::sendMessageToUser);

        long totalEnd = System.nanoTime(); // 전체 시간 측정 끝
        long durationS = (totalEnd - totalStart) / 1_000_000_000;
        long durationMs = (totalEnd - totalStart) / 1_000_000;
        log.info("🔥 [전체 Slack 전송 소요 시간]: {} s {} ms", durationS, durationMs);
    }

    private Map<String, String> fetchAlertTargets(
            List<NotificationRegion> notificationRegionList,
            List<AverageRegion> averageRegionList
    ) {
        Map<String, String> alertMap = new HashMap<>();

        for (NotificationRegion notification : notificationRegionList) {

            averageRegionList.stream()
                    .filter(averageRegion -> averageRegion.getRegionName().equals(notification.getRegion().getName()))
                    .findFirst()
                    .ifPresent(averageRegion -> {
                        int pm10 = Integer.parseInt(averageRegion.getPm10Value());
                        int pm25 = Integer.parseInt(averageRegion.getPm25Value());

                        if (pm10 > notification.getPm10Threshold() || pm25 > notification.getPm25Threshold()) {
                            String message = buildAlertMessage(notification.getRegion().getName(), pm10, pm25, notification);
                            alertMap.put(notification.getUser().getSlackId(), message);
                        }
                    });
        }

        return alertMap;
    }

    private String buildAlertMessage(String regionName, int pm10, int pm25, NotificationRegion n) {
        StringBuilder message = new StringBuilder("[")
                .append(regionName)
                .append("] 대기오염 알림\n");

        if (pm10 > n.getPm10Threshold()) {
            message.append(" - 미세먼지(PM10): ").append(pm10)
                    .append("㎍/㎥ (설정 임계치: ").append(n.getPm10Threshold()).append(")\n");
        }

        if (pm25 > n.getPm25Threshold()) {
            message.append(" - 초미세먼지(PM2.5): ").append(pm25)
                    .append("㎍/㎥ (설정 임계치: ").append(n.getPm25Threshold()).append(")\n");
        }

        return message.toString();
    }
}
