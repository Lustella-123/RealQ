package com.example.realq.domain.realtime.region.service;

import com.example.realq.domain.realtime.region.client.RegionApiClient;
import com.example.realq.domain.realtime.region.dto.response.RegionItem;
import com.example.realq.domain.realtime.region.dto.response.RegionResponse;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionApiClient apiClient;

    public List<RegionResponse> getByRegion(String region) {
        List<RegionItem> items = apiClient.getRegionData(region);

        if (items.isEmpty()) {
            throw new GlobalException(ErrorCode.AIR_QUALITY_NOT_AVAILABLE);
        }

        return items.stream()
                .map(item -> new RegionResponse(
                        item.stationName(),
                        item.dataTime(),
                        Double.parseDouble(item.pm10Value()),
                        Double.parseDouble(item.pm25Value()),
                        Double.parseDouble(item.o3Value()),
                        Double.parseDouble(item.no2Value()),
                        Double.parseDouble(item.coValue()),
                        Double.parseDouble(item.so2Value())
                ))
                .collect(Collectors.toList());
    }
}
