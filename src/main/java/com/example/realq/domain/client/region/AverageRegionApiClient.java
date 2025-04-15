package com.example.realq.domain.client.region;

import com.example.realq.domain.average.region.SearchConditionEnum;
import com.example.realq.domain.average.region.dto.response.AverageRegionItem;
import com.example.realq.domain.average.region.dto.response.AverageRegionWrapper;
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
public class AverageRegionApiClient {

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    @Value("${airkorea.api.service-key}")
    private String serviceKey;

    public AverageRegionItem[] getData(String region, SearchConditionEnum period) {
        try {
            String encodedRegion = URLEncoder.encode(region, StandardCharsets.UTF_8);
            String encodedPeriod = URLEncoder.encode(period.name(), StandardCharsets.UTF_8);
            String url = String.format(
                    "http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst" +
                            "?sidoName=%s&returnType=json&searchCondition=%s&serviceKey=%s",
                    encodedRegion, encodedPeriod, serviceKey
            );
            URI uri = new URI(url);
            AverageRegionWrapper wrapper = restTemplate.getForEntity(uri, AverageRegionWrapper.class)
                    .getBody();
            return wrapper.response().body().items();

//            AverageRegionWrapper responseWrapper = webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//                            .scheme("http")
//                            .host("apis.data.go.kr")
//                            .path("/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst")
//                            .queryParam("sidoName", region)
//                            .queryParam("returnType", "json")
//                            .queryParam("searchCondition", period.name())
//                            .queryParam("serviceKey", serviceKey)
//                            .build())
//                    .retrieve()
//                    .bodyToMono(AverageRegionWrapper.class)
//                    .block();
//
//            return responseWrapper.response().body().items();
        } catch (Exception e) {
            return new AverageRegionItem[0];
        }
    }
}
