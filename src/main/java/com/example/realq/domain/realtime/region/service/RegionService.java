package com.example.realq.domain.realtime.region.service;

import com.example.realq.domain.realtime.region.dto.response.RegionItem;
import com.example.realq.domain.realtime.region.dto.response.RegionResponse;
import com.example.realq.domain.realtime.region.dto.response.RegionResponseBody;
import com.example.realq.domain.realtime.region.dto.response.RegionWrapper;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final WebClient webClient;
    private final RestTemplate restTemplate;

    @Value("${airkorea.api.service-key}")
    private String serviceKey;

    public RegionResponse getByRegion(String region) {
        try {
            String encodedRegion = URLEncoder.encode(region, StandardCharsets.UTF_8);

            String rawUrl = String.format(
                    "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty" +
                            "?sidoName=%s&returnType=json&serviceKey=%s&ver=1.0",
                    encodedRegion, serviceKey
            );

            URI uri = new URI(rawUrl);
            ResponseEntity<RegionWrapper> response = restTemplate.getForEntity(uri, RegionWrapper.class);
            RegionResponseBody responseBody = response.getBody().response();
            List<RegionItem> items = responseBody.body().items();

//            RegionWrapper responseWrapper = webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//                            .scheme("http")
//                            .host("apis.data.go.kr")
//                            .path("/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty")
//                            .queryParam("sidoName", encodedRegion)
//                            .queryParam("returnType", "json")
//                            .queryParam("serviceKey", serviceKey)
//                            .queryParam("ver", "1.0")
//                            .build())
//                    .retrieve()
//                    .bodyToMono(RegionWrapper.class)
//                    .block();

//            RegionResponseBody responseBody = responseWrapper.response();
//            List<RegionItem> items = responseBody.body().items();

            int count = 0;
            double pm10Sum = 0, pm25Sum = 0, o3Sum = 0, no2Sum = 0, coSum = 0, so2Sum = 0;
            String latestTime = "";

            for (RegionItem item : items) {
                try {
                    pm10Sum += Double.parseDouble(item.pm10Value());
                    pm25Sum += Double.parseDouble(item.pm25Value());
                    o3Sum   += Double.parseDouble(item.o3Value());
                    no2Sum  += Double.parseDouble(item.no2Value());
                    coSum   += Double.parseDouble(item.coValue());
                    so2Sum  += Double.parseDouble(item.so2Value());
                    count++;

                    if (latestTime.isEmpty()) {
                        latestTime = item.dataTime();
                    }
                } catch (Exception ignored) {
                    // 값이 "—" 같은 경우
                }
            }

            return new RegionResponse(
                    region,
                    latestTime,
                    count > 0 ? pm10Sum / count : 0,
                    count > 0 ? pm25Sum / count : 0,
                    count > 0 ? o3Sum / count : 0,
                    count > 0 ? no2Sum / count : 0,
                    count > 0 ? coSum / count : 0,
                    count > 0 ? so2Sum / count : 0
            );

        } catch (Exception e) {
            throw new GlobalException(ErrorCode.REGION_API_ERROR);
        }
    }
}
