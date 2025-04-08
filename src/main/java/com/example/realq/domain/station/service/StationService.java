package com.example.realq.domain.station.service;

import com.example.realq.domain.station.dto.response.StationItem;
import com.example.realq.domain.station.dto.response.StationResponse;
import com.example.realq.domain.station.dto.response.StationResponseBody;
import com.example.realq.domain.station.dto.response.StationWrapper;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class StationService {

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    @Value("${airkorea.api.service-key}")
    private String serviceKey;

    public StationResponse getByStation(String station) {
        try {
            String encodedStation = URLEncoder.encode(station, StandardCharsets.UTF_8);
            String rawUrl = String.format(
                    "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty" +
                            "?stationName=%s&dataTerm=DAILY&returnType=json&serviceKey=%s&ver=1.0",
                    encodedStation, serviceKey
            );

            URI uri = new URI(rawUrl);
            StationResponseBody responseBody = restTemplate.getForEntity(uri, StationWrapper.class).getBody().response();

//            StationWrapper responseWrapper = webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//                            .scheme("http")
//                            .host("apis.data.go.kr")
//                            .path("/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
//                            .queryParam("stationName", encodedStation)
//                            .queryParam("dataTerm", "DAILY")
//                            .queryParam("returnType", "json")
//                            .queryParam("serviceKey", serviceKey)
//                            .queryParam("ver", "1.0")
//                            .build())
//                    .retrieve()
//                    .bodyToMono(StationWrapper.class)
//                    .block();
//
//            StationResponseBody responseBody = responseWrapper.response();

            StationItem[] items = responseBody.body().items();
            if (items.length == 0) {
                throw new GlobalException(ErrorCode.AIR_QUALITY_NOT_AVAILABLE);
            }

            StationItem item = items[0];
            return new StationResponse(
                    item.stationName(),
                    item.dataTime(),
                    parseDouble(item.pm10Value()),
                    parseDouble(item.pm25Value()),
                    parseDouble(item.o3Value()),
                    parseDouble(item.no2Value()),
                    parseDouble(item.coValue()),
                    parseDouble(item.so2Value())
            );

        } catch (Exception e) {
            throw new GlobalException(ErrorCode.STATION_API_ERROR);
        }
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
