package com.example.realq.domain.slack.service;

import com.example.realq.domain.notificationregion.entity.NotificationRegion;
import com.example.realq.domain.notificationregion.repository.NotificationRegionRepository;
import com.example.realq.domain.realtime.region.entity.Region;
import com.example.realq.domain.slack.client.DistrictSaveClient;
import com.example.realq.domain.slack.entity.AverageDistrict;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SlackUserProvider {

    private final SlackService slackService;
    private final DistrictSaveClient districtSaveClient;
    private final NotificationRegionRepository notificationRegionRepository;

    @Scheduled(cron = "0 */1 * * * *")
    public void sendNotificationToAllUsers() {
        log.info("메서드 실행: sendNotificationToAllUsers");

        // 1. 가장 최근 측정된 AverageDistrict 데이터 저장 & 가져오기
        List<AverageDistrict> averageDistricts = districtSaveClient.saveAverageDistrict();

        // 2. 모든 NotificationRegion을 가져와 비교
        List<NotificationRegion> notificationRegions = notificationRegionRepository.findAll();

        Map<String, String> userSlackIdToMessage = new HashMap<>();

        for (NotificationRegion notification : notificationRegions) {
            if (!notification.isEnabled()) continue;

            String slackId = notification.getUser().getSlackId();
            Region region = notification.getRegion();

            // 해당 유저가 등록한 지역명과 일치하는 평균 데이터 찾기
            Optional<AverageDistrict> optionalAverage = averageDistricts.stream()
                    .filter(avg -> avg.getRegionName().equals(region.getName()))
                    .findFirst();

            if (optionalAverage.isEmpty()) continue;

            AverageDistrict avg = optionalAverage.get();
            int pm10 = Integer.parseInt(avg.getPm10Value());
            int pm25 = Integer.parseInt(avg.getPm25Value());

            if (pm10 > notification.getPm10Threshold() || pm25 > notification.getPm25Threshold()) {
                StringBuilder message = new StringBuilder();
                message.append("🌫️ [").append(region.getName()).append("] 대기오염 알림\n");
                if (pm10 > notification.getPm10Threshold()) {
                    message.append(" - 미세먼지(PM10): ").append(pm10).append("㎍/㎥ (사용자가 설정한 임계치: ").append(notification.getPm10Threshold()).append(")\n");
                }
                if (pm25 > notification.getPm25Threshold()) {
                    message.append(" - 초미세먼지(PM2.5): ").append(pm25).append("㎍/㎥ (사용자가 설정한 임계치: ").append(notification.getPm25Threshold()).append(")\n");
                }

                userSlackIdToMessage.put(slackId, message.toString());
            }
        }

        // 3. 사용자별로 메시지 전송
        userSlackIdToMessage.forEach(slackService::sendMessageToUser);
    }
}
