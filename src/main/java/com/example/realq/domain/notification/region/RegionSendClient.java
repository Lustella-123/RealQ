package com.example.realq.domain.notification.region;

import com.example.realq.domain.notificationregion.entity.NotificationRegion;
import com.example.realq.domain.notificationregion.repository.NotificationRegionRepository;
import com.example.realq.domain.average.region.entity.AverageRegion;
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

    @Scheduled(cron = "0 0 5,12,17 * * *")
    public void sendAverageRegion() {
        log.info("메서드 실행: sendAverageRegion");

        List<AverageRegion> averageRegions = regionGetClient.getAverageRegion();
        List<NotificationRegion> notificationRegions = notificationRegionRepository.findAll();

        Map<String, String> userSlackIdToMessage = fetchAlertTargets(notificationRegions, averageRegions);

        userSlackIdToMessage.forEach(slackService::sendMessageToUser);
    }

    private Map<String, String> fetchAlertTargets(List<NotificationRegion> notifications, List<AverageRegion> data) {
        Map<String, String> alertMap = new HashMap<>();

        for (NotificationRegion notification : notifications) {
            if (!notification.isEnabled()) continue;

            data.stream()
                    .filter(avg -> avg.getRegionName().equals(notification.getRegion().getName()))
                    .findFirst()
                    .ifPresent(avg -> {
                        int pm10 = Integer.parseInt(avg.getPm10Value());
                        int pm25 = Integer.parseInt(avg.getPm25Value());

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
