package com.example.realq.domain.client.station;

import com.example.realq.domain.average.station.AverageStation;
import com.example.realq.domain.notification.station.entity.NotificationStation;
import com.example.realq.domain.notification.station.repository.NotificationStationRepository;
import com.example.realq.domain.slack.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StationSendClient {

    private final SlackService slackService;
    private final StationGetClient stationGetClient;
    private final NotificationStationRepository notificationStationRepository;

    @Scheduled(cron = "0 0 7,12,17 * * *")
    public void sendAverageStation() {
        log.info("메서드 실행: sendAverageStation");

        List<AverageStation> averageStationList = stationGetClient.getAverageStation();

        List<NotificationStation> notificationStationList = notificationStationRepository.findAllByEnabledTrue();

        Map<String, String> userSlackIdToMessage = fetchAlertTargets(notificationStationList, averageStationList);

        userSlackIdToMessage.forEach(slackService::sendMessageToUser);

    }

    private Map<String, String> fetchAlertTargets(
            List<NotificationStation> notificationStationList,
            List<AverageStation> averageStationList
    ) {
        Map<String, String> alertMap = new HashMap<>();

        for (NotificationStation notification : notificationStationList) {

            String stationName = notification.getStation().getName();

            averageStationList.stream()
                    .filter(averageStation -> averageStation.getStationName().equals(stationName))
                    .findFirst()
                    .ifPresent(averageStation -> {
                        int pm10 = Integer.parseInt(averageStation.getPm10Value());
                        int pm25 = Integer.parseInt(averageStation.getPm25Value());

                        if (pm10 > notification.getPm10Threshold() || pm25 > notification.getPm25Threshold()) {
                            String message = buildAlertMessage(stationName, pm10, pm25, notification);
                            alertMap.put(notification.getUser().getSlackId(), message);
                        }
                    });
        }

        return alertMap;
    }

    private String buildAlertMessage(String stationName, int pm10, int pm25, NotificationStation notificationStation) {
        StringBuilder message = new StringBuilder("*:warning: 대기오염 경보 :warning:*\n")
                .append("*측정소 위치:* `").append(stationName).append("`\n\n");

        if (pm10 > notificationStation.getPm10Threshold()) {
            message.append("> *미세먼지 (PM10)*: ")
                    .append("*").append(pm10).append("㎍/㎥* ")
                    .append("(임계치: ").append(notificationStation.getPm10Threshold()).append("㎍/㎥)\n");
        }

        if (pm25 > notificationStation.getPm25Threshold()) {
            message.append("> *초미세먼지 (PM2.5)*: ")
                    .append("*").append(pm25).append("㎍/㎥* ")
                    .append("(임계치: ").append(notificationStation.getPm25Threshold()).append("㎍/㎥)\n");
        }

        return message.toString();
    }
}