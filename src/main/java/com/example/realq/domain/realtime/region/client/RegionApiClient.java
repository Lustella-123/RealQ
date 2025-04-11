package com.example.realq.domain.realtime.region.client;

import com.example.realq.domain.realtime.region.dto.response.RegionItem;
import com.example.realq.domain.realtime.region.dto.response.RegionWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegionApiClient {

    private final RestTemplate restTemplate;

    @Value("${airkorea.api.service-key}")
    private String serviceKey;

    public List<RegionItem> getRegionData(String region) {
        try {
            String encodedRegion = URLEncoder.encode(region, StandardCharsets.UTF_8);
            String url = String.format(
                    "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty" +
                            "?sidoName=%s&returnType=json&serviceKey=%s&ver=1.0",
                    encodedRegion, serviceKey
            );
            URI uri = new URI(url);
            RegionWrapper wrapper = restTemplate.getForEntity(uri, RegionWrapper.class).getBody();
            return wrapper.response().body().items();
        } catch (Exception e) {
            return List.of();
        }
    }
}
