package com.example.realq.domain.realtime.region.service;

import com.example.realq.domain.realtime.region.client.RealtimeRegionApiClient;
import com.example.realq.domain.realtime.region.dto.response.RealtimeRegionItem;
import com.example.realq.domain.realtime.region.dto.response.RealtimeRegionResponse;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RealtimeRegionService {

    private final RealtimeRegionApiClient apiClient;

    public List<RealtimeRegionResponse> getByRegion(String region) {
        List<RealtimeRegionItem> realtimeRegionItems = apiClient.getRegionData(region);

        if (realtimeRegionItems.isEmpty()) {
            throw new GlobalException(ErrorCode.AIR_QUALITY_NOT_AVAILABLE);
        }

        return realtimeRegionItems.stream()
                .map(item -> new RealtimeRegionResponse(
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
