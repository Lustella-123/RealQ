package com.example.realq.domain.avg.district.client;

import com.example.realq.domain.avg.district.SearchConditionEnum;
import com.example.realq.domain.avg.district.dto.response.AvgDistrictItem;
import com.example.realq.domain.avg.district.dto.response.AvgDistrictWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class AvgDistrictApiClient {

    private final RestTemplate restTemplate;

    @Value("${airkorea.api.service-key}")
    private String serviceKey;

    public AvgDistrictItem[] getData(String region, SearchConditionEnum period) {
        try {
            String encodedRegion = URLEncoder.encode(region, StandardCharsets.UTF_8);
            String encodedPeriod = URLEncoder.encode(period.name(), StandardCharsets.UTF_8);
            String url = String.format(
                    "http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst" +
                            "?sidoName=%s&returnType=json&searchCondition=%s&serviceKey=%s",
                    encodedRegion, encodedPeriod, serviceKey
            );
            URI uri = new URI(url);
            return restTemplate.getForEntity(uri, AvgDistrictWrapper.class)
                    .getBody()
                    .response()
                    .body()
                    .items();
        } catch (Exception e) {
            return new AvgDistrictItem[0];
        }
    }
}
