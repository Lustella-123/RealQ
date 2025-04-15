package com.example.realq.domain.realtime.station.client;

import com.example.realq.domain.realtime.station.dto.response.RealtimeStationItem;
import com.example.realq.domain.realtime.station.dto.response.RealtimeStationWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class RealtimeStationApiClient {

    private final RestTemplate restTemplate;
    private final WebClient webClient;

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
            RealtimeStationWrapper wrapper = restTemplate.getForEntity(uri, RealtimeStationWrapper.class).getBody();
            return wrapper.response().body().items();

//            RealtimeStationWrapper responseWrapper = webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//                            .scheme("http")
//                            .host("apis.data.go.kr")
//                            .path("/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
//                            .queryParam("stationName", station)
//                            .queryParam("dataTerm", "DAILY")
//                            .queryParam("returnType", "json")
//                            .queryParam("serviceKey", serviceKey)
//                            .queryParam("ver", "1.0")
//                            .build())
//                    .retrieve()
//                    .bodyToMono(RealtimeStationWrapper.class)
//                    .block();
//
//            return responseWrapper.response().body().items();
        } catch (Exception e) {
            return new RealtimeStationItem[0];
        }
    }
}
