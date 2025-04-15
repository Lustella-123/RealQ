package com.example.realq.domain.realtime.region.client;

import com.example.realq.domain.realtime.region.dto.response.RealtimeRegionItem;
import com.example.realq.domain.realtime.region.dto.response.RealtimeRegionWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RealtimeRegionApiClient {

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    @Value("${airkorea.api.service-key}")
    private String serviceKey;

    public List<RealtimeRegionItem> getRegionData(String region) {
        try {
            String encodedRegion = URLEncoder.encode(region, StandardCharsets.UTF_8);
            String url = String.format(
                    "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty" +
                            "?sidoName=%s&returnType=json&serviceKey=%s&ver=1.0",
                    encodedRegion, serviceKey
            );
            URI uri = new URI(url);
            RealtimeRegionWrapper wrapper = restTemplate.getForEntity(uri, RealtimeRegionWrapper.class).getBody();

            return wrapper.response().body().items();

//            RealtimeRegionWrapper responseWrapper = webClient.get()
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
//                    .bodyToMono(RealtimeRegionWrapper.class)
//                    .block();
//
//            return responseWrapper.response().body().items();
        } catch (Exception e) {
            return List.of();
        }
    }
}
