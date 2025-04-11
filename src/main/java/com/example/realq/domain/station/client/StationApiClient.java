package com.example.realq.domain.station.client;

import com.example.realq.domain.station.dto.response.StationItem;
import com.example.realq.domain.station.dto.response.StationWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class StationApiClient {

    private final RestTemplate restTemplate;

    @Value("${airkorea.api.service-key}")
    private String serviceKey;

    public StationItem[] getStationData(String station) {
        try {
            String encodedStation = URLEncoder.encode(station, StandardCharsets.UTF_8);
            String url = String.format(
                    "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty" +
                            "?stationName=%s&dataTerm=DAILY&returnType=json&serviceKey=%s&ver=1.0",
                    encodedStation, serviceKey
            );
            URI uri = new URI(url);
            return restTemplate.getForEntity(uri, StationWrapper.class)
                    .getBody()
                    .response()
                    .body()
                    .items();
        } catch (Exception e) {
            return new StationItem[0];
        }
    }
}
