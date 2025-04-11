package com.example.realq.domain.realtime.station.client;

import com.example.realq.domain.realtime.station.dto.response.RealtimeStationItem;
import com.example.realq.domain.realtime.station.dto.response.RealtimeStationWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class RealtimeStationApiClient {

    private final RestTemplate restTemplate;

    @Value("${airkorea.api.service-key}")
    private String serviceKey;

    public RealtimeStationItem[] getStationData(String station) {
        try {
            String encodedStation = URLEncoder.encode(station, StandardCharsets.UTF_8);
            String url = String.format(
                    "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty" +
                            "?stationName=%s&dataTerm=DAILY&returnType=json&serviceKey=%s&ver=1.0",
                    encodedStation, serviceKey
            );
            URI uri = new URI(url);
            return restTemplate.getForEntity(uri, RealtimeStationWrapper.class)
                    .getBody()
                    .response()
                    .body()
                    .items();
        } catch (Exception e) {
            return new RealtimeStationItem[0];
        }
    }
}
